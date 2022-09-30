package com.weasel.modules.dev.controller

import ai.yue.library.base.view.R
import ai.yue.library.base.view.Result
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper
import com.weasel.modules.dev.service.DevDatasourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.sql.DataSource
import java.sql.DatabaseMetaData
import java.sql.ResultSet

/**
 * @author weasel
 * @date 2022/8/2 9:05
 * @version 1.0
 */
@RestController
@RequestMapping('dbMetaData')
class DbMetaDataController {
    @Autowired
    DataSource dataSource
    @Autowired
    DevDatasourceService devDatasourceService

    def getDataSource(String name) {
        if (dataSource instanceof DynamicRoutingDataSource) {
            def dynamicRoutingDataSource = dataSource as DynamicRoutingDataSource
            dynamicRoutingDataSource.getDataSource(name)
        } else {
            dataSource
        }
    }

    @GetMapping('tables')
    Result tables(String name) {
        def dataSource = getDataSource(name)

        def connection = dataSource.getConnection()
        DatabaseMetaData dbMetaData = connection.getMetaData();
        ResultSet rs = dbMetaData.getIndexInfo('weasel8', null, "sys_config", true, false);
        while (rs.next()) {
            println rs.getProperties() //提取索引名称
            println 'TABLE_CAT====' + rs.getString("TABLE_CAT") //提取索引名称
            println 'TABLE_SCHEM====' + rs.getString("TABLE_SCHEM") //提取索引名称
            println 'TABLE_NAME====' + rs.getString("TABLE_NAME") //提取索引名称
            println 'NON_UNIQUE====' + rs.getBoolean("NON_UNIQUE") //提取索引名称
            println 'INDEX_QUALIFIER====' + rs.getBoolean("INDEX_QUALIFIER") //提取索引名称
            println 'INDEX_NAME====' + rs.getString("INDEX_NAME") //提取索引名称
            println 'TYPE====' + rs.getShort("TYPE") //提取索引名称
            println 'ORDINAL_POSITION====' + rs.getShort("ORDINAL_POSITION") //提取索引名称
            println 'COLUMN_NAME====' + rs.getString("COLUMN_NAME") //提取索引名称
            println 'ASC_OR_DESC====' + rs.getString("ASC_OR_DESC") //提取索引名称
            println 'CARDINALITY====' + rs.getLong("CARDINALITY") //提取索引名称
            println 'PAGES====' + rs.getLong("PAGES") //提取独特的信息
            println 'FILTER_CONDITION====' + rs.getString("FILTER_CONDITION") //提取序数位置
            println '-----------------------'
            // work with ResultSet
        }
//        DatabaseMetaDataWrapper databaseMetaDataWrapper = new DatabaseMetaDataWrapper(connection);
//        List<DatabaseMetaDataWrapper.Table> tables = databaseMetaDataWrapper.getTables(databaseMetaDataWrapper.catalog, null, "sys_config", ['INDEX'] as String[]);
//
//        R.success tables
//        R.success TableInfoHelper.tableInfos
    }

    @GetMapping('columns')
    Result columns(String name) {
        def dataSource = getDataSource(name)

        def connection = dataSource.getConnection()
        DatabaseMetaDataWrapper databaseMetaDataWrapper = new DatabaseMetaDataWrapper(connection);
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfo = databaseMetaDataWrapper.getColumnsInfo(databaseMetaDataWrapper.catalog, null, "dev_field", true);

        R.success columnsInfo.values()
    }
}
