package com.weasel.config;

import com.alibaba.fastjson.JSONArray;
import com.ejlchina.json.JSONKit;
import com.ejlchina.searcher.BeanMeta;
import com.ejlchina.searcher.ParamFilter;
import com.ejlchina.searcher.SearchSql;
import com.ejlchina.searcher.SqlInterceptor;
import com.ejlchina.searcher.param.FetchType;
import com.weasel.common.consts.Consts;
import io.github.heykb.sqlhelper.config.SqlHelperAutoDbType;
import io.github.heykb.sqlhelper.helper.SqlStatementEditor;
import io.github.heykb.sqlhelper.interceptor.SqlHelperPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/20 11:01
 */
@Configuration
@Profile(Consts.Profile.NOT_ACTABLE)
public class BeanSearcherConfig {
    /**
     * 整合mybatis-sqlHelper
     *
     * @return
     */
    @Bean
    public SqlInterceptor mybatisSqlHelperInterceptor(DataSource dataSource, SqlHelperPlugin sqlHelperPlugin) {
        return new SqlInterceptor() {
            @Override
            public <T> SearchSql<T> intercept(SearchSql<T> searchSql, Map<String, Object> map, FetchType fetchType) {
                if (searchSql.isShouldQueryList()) {
                    searchSql.setListSqlString(changeSql(searchSql.getListSqlString(), dataSource, sqlHelperPlugin));
                }
                if (searchSql.isShouldQueryCluster()) {
                    searchSql.setClusterSqlString(changeSql(searchSql.getClusterSqlString(), dataSource, sqlHelperPlugin));
                }
                return searchSql;
            }

            private String changeSql(String sql, DataSource dataSource, SqlHelperPlugin sqlHelperPlugin) {
                SqlStatementEditor sqlStatementEditorFactory = new SqlStatementEditor.Builder(sql, SqlHelperAutoDbType.getDbType(dataSource))
                        .injectColumnInfoHandlers(sqlHelperPlugin.getInjectColumnInfoHandlers())
                        .columnFilterInfoHandlers(sqlHelperPlugin.getColumnFilterInfoHandlers())
                        .build();
                return sqlStatementEditorFactory.processing().getSql();
            }
        };
    }

    @Bean
    public ParamFilter betweenParamFilter() {
        return new ParamFilter() {

            final String OP_SUFFIX = "-op";

            @Override
            public <T> Map<String, Object> doFilter(BeanMeta<T> beanMeta, Map<String, Object> paraMap) {
                Map<String, Object> newParaMap = new HashMap<>();
                paraMap.forEach((key, value) -> {
                    if (key == null) {
                        return;
                    }
                    boolean isOpKey = key.endsWith(OP_SUFFIX);
                    String opKey = isOpKey ? key : key + OP_SUFFIX;
                    Object opVal = paraMap.get(opKey);
                    if (!"mv".equals(opVal) && !"bt".equals(opVal) && !"il".equals(opVal)) {
                        newParaMap.put(key, value);
                        return;
                    }
                    if (newParaMap.containsKey(key)) {
                        return;
                    }
                    String valKey = key;
                    Object valVal = value;
                    if (isOpKey) {
                        valKey = key.substring(0, key.length() - OP_SUFFIX.length());
                        valVal = paraMap.get(valKey);
                    }
                    if (likelyJsonArr(valVal)) {
                        try {
                            String vKey = valKey;
                            JSONKit.toArray((String) valVal).forEach(
                                    (index, data) -> newParaMap.put(vKey + "-" + index, data.toString())
                            );
                            newParaMap.put(opKey, opVal);
                            return;
                        } catch (Exception ignore) {
                        }
                    }
                    if (valVal instanceof JSONArray) {
                        try {
                            String vKey = valKey;
                            JSONArray jsonArray = (JSONArray) valVal;
                            for (int i = 0; i < jsonArray.size(); i++) {
                                Object o = jsonArray.get(i);
                                newParaMap.put(vKey + "-" + i, o.toString());
                            }
                            newParaMap.put(opKey, opVal);
                            return;
                        } catch (Exception ignore) {
                        }
                    }
                    newParaMap.put(key, value);
                });
                return newParaMap;
            }

            private boolean likelyJsonArr(Object value) {
                if (value instanceof String) {
                    String str = ((String) value).trim();
                    return str.startsWith("[") && str.endsWith("]");
                }
                return false;
            }

        };
    }
}
