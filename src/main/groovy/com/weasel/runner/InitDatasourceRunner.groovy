//package com.weasel.runner
//
//import com.alibaba.druid.pool.DruidDataSource
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource
//import com.baomidou.mybatisplus.core.toolkit.Wrappers
//import com.weasel.common.consts.Consts
//import com.weasel.modules.dev.entity.DevDatasource
//import com.weasel.modules.dev.service.DevDatasourceService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.ApplicationArguments
//import org.springframework.boot.ApplicationRunner
//import org.springframework.context.annotation.Profile
//import org.springframework.stereotype.Component
//
//import javax.sql.DataSource
//
///**
// * @author weasel
// * @date 2022/8/2 9:23
// * @version 1.0
// */
//@Component
//@Profile(Consts.Profile.NOT_ACTABLE)
//class InitDatasourceRunner implements ApplicationRunner {
//    @Autowired
//    DataSource dataSource
//    @Autowired
//    DevDatasourceService devDatasourceService
//
//    @Override
//    void run(ApplicationArguments args) throws Exception {
//        if (dataSource instanceof DynamicRoutingDataSource) {
//            def dynamicRoutingDataSource = dataSource as DynamicRoutingDataSource
//            def dataSources = dynamicRoutingDataSource.dataSources
//            def devDatasources = []
//            dataSources.each { key, value ->
//                def count = devDatasourceService.count(Wrappers.<DevDatasource> query().eq('name', key))
//                if (!count) {
//                    def druidDataSource = value.realDataSource as DruidDataSource
//                    devDatasources << new DevDatasource(name: key, driverClassName: druidDataSource.driverClassName, url: druidDataSource.url, username: druidDataSource.username, password: druidDataSource.password)
//                }
//            }
//            devDatasourceService.saveBatch(devDatasources)
//        }
//    }
//}
