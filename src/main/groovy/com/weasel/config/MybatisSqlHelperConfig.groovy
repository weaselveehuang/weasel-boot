package com.weasel.config

import cn.dev33.satoken.stp.StpUtil
import com.alibaba.druid.DbType
import com.alibaba.druid.sql.SQLUtils
import com.alibaba.druid.sql.ast.SQLExpr
import com.weasel.common.consts.Consts
import com.weasel.modules.sys.enums.DataScope
import io.github.heykb.sqlhelper.handler.InjectColumnInfoHandler
import io.github.heykb.sqlhelper.handler.abstractor.ConditionInjectInfoHandler
import io.github.heykb.sqlhelper.handler.abstractor.LogicDeleteInfoHandler
import io.github.heykb.sqlhelper.handler.abstractor.TenantInfoHandler
import io.github.heykb.sqlhelper.handler.dynamic.DynamicFindInjectInfoHandler
import io.github.heykb.sqlhelper.interceptor.SqlHelperPlugin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * @author weasel
 * @date 2022/4/14 9:35
 * @version 1.0
 */
@Configuration
class MybatisSqlHelperConfig {
    @Autowired
    StpInterfaceImpl authInterface

    @Bean
    @Profile(Consts.Profile.NOT_ACTABLE)
    SqlHelperPlugin sqlHelperPlugin() {
        List<InjectColumnInfoHandler> injectColumnInfoHandlers = []
        SqlHelperPlugin sqlHelperPlugin = new SqlHelperPlugin()
        // 多租户插件
        TenantInfoHandler tenantInfoHandler = new TenantInfoHandler() {
            @Override
            String getTenantIdColumn() {
                "tenant_id"
            }

            @Override
            String getTenantId() {
                "1"
            }
        }
        injectColumnInfoHandlers << tenantInfoHandler

        // 逻辑删除插件
        LogicDeleteInfoHandler logicDeleteInfoHandler = new LogicDeleteInfoHandler() {
            @Override
            String getDeleteSqlDemo() {
                // FIXME: 逻辑删除时记录删除时间和删除操作人，支持mysql，其它数据库需修改
                // 将删除标记设置默认值(例如0)，将唯一字段与删除标记添加唯一键约束。当某一记录需要删除时，将删除标记置为NULL。
                // 由于NULL不会和其他字段有组合唯一键的效果，所以当记录被删除时(删除标记被置为NULL时)，解除了唯一键的约束。此外该方法能很好地解决批量删除的问题(只要置为NULL就完事了)，消耗的空间也并不多(1位 + 联合索引)
                "UPDATE xx SET deleted = null, update_time = now(), update_by = ${StpUtil.getLoginId()}"
            }

            @Override
            String getNotDeletedValue() {
                // FIXME: 逻辑删除字段默认值变更时需修改
                "false"
            }

            @Override
            String getColumnName() {
                // FIXME: 逻辑删除字段名称变更时需修改
                "deleted"
            }
        }
        injectColumnInfoHandlers << logicDeleteInfoHandler
        sqlHelperPlugin.injectColumnInfoHandlers = injectColumnInfoHandlers

        DynamicFindInjectInfoHandler dynamicFindInjectInfoHandler = new DynamicFindInjectInfoHandler() {
            @Override
            List<InjectColumnInfoHandler> findInjectInfoHandlers() {

                // 数据权限插件
                ConditionInjectInfoHandler conditionInjectInfoHandler = new ConditionInjectInfoHandler() {
                    List<DataScope> dataScopes

                    @Override
                    String getColumnName() {
                        'create_by'
                    }

                    @Override
                    String getValue() {
                        def sql
                        dataScopes = dataScopes ?: authInterface.getDataScopes()
                        switch (dataScopes) {
                            case { DataScope.LEVEL_CHILDREN in dataScopes || (DataScope.LEVEL in dataScopes && DataScope.SELF_CHILDREN in dataScopes) }:
                                sql = "select user_id from sys_user_dept where dept_id in (select dept_id from sys_user_dept where user_id=${StpUtil.getLoginIdAsString()})"
                                break
                            case { DataScope.LEVEL in dataScopes }:
                                sql = "select user_id from sys_user_dept where dept_id in (select dept_id from sys_user_dept where user_id=${StpUtil.getLoginIdAsString()})"
                                break
                            case { DataScope.SELF_CHILDREN in dataScopes }:
                                sql = "select user_id from sys_user_dept where dept_id in (select dept_id from sys_user_dept where user_id=${StpUtil.getLoginIdAsString()})"
                                break
                            case { DataScope.SELF in dataScopes }:
                                sql = StpUtil.getLoginIdAsString()
                                break
                        }
                        "(${sql})"
                    }

                    @Override
                    String op() {
                        'in'
                    }

                    @Override
                    boolean checkTableName(String tableName) {
                        !tableName.startsWithIgnoreCase("sys_") && !tableName.startsWithIgnoreCase("dev_")
                    }

                    @Override
                    SQLExpr toConditionSQLExpr(String tableAlias, DbType dbType, Map<String, String> columnAliasMap, boolean isMapUnderscoreToCamelCase) {
                        dataScopes = dataScopes ?: authInterface.getDataScopes()
                        DataScope.ALL in dataScopes ? SQLUtils.toSQLExpr('true') : super.toConditionSQLExpr(tableAlias, dbType, columnAliasMap, isMapUnderscoreToCamelCase)
                    }
                }
                [conditionInjectInfoHandler]
            }
        }
        sqlHelperPlugin.dynamicFindInjectInfoHandler = dynamicFindInjectInfoHandler
        return sqlHelperPlugin
    }
}
