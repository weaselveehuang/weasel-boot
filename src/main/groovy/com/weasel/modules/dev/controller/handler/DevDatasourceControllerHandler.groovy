package com.weasel.modules.dev.controller.handler


import com.baomidou.dynamic.datasource.DynamicRoutingDataSource
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty
import com.weasel.common.base.IControllerHandler
import com.weasel.modules.dev.entity.DevDatasource
import com.weasel.modules.dev.service.DevDatasourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.sql.DataSource

/**
 * @author weasel
 * @date 2022/7/25 9:45
 * @version 1.0
 */
@RestController
@RequestMapping("/dev/datasource")
class DevDatasourceControllerHandler implements IControllerHandler<DevDatasource> {
    @Autowired
    DataSource dataSource
    @Autowired
    DruidDataSourceCreator dataSourceCreator
    @Autowired
    DevDatasourceService devDatasourceService

    @Override
    void beforeSave(DevDatasource entity) {
        addDataSource(entity)
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, entity.getName())), "已存在相同名称记录！");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, entity.getCode())), "已存在相同值记录！");
    }

    @Override
    void beforeUpdate(DevDatasource entity) {
        addDataSource(entity)
//        Assert.isFalse(EnumUtil.contains(DefaultRole.class, entity.getCode()), "系统默认角色不允许编辑！");
//        CommonControllerHandler.super.beforeUpdate(entity);
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, entity.getId()).eq(SysRole::getName, entity.getName())), "已存在相同名称记录！");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, entity.getId()).eq(SysRole::getCode, entity.getCode())), "已存在编码名称记录！");
    }

    @Override
    void beforeDelete(DevDatasource entity) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, id).in(SysRole::getCode, EnumUtil.getFieldValues(DefaultRole.class, "value"))), "所选角色为系统默认角色，不能删除！");
//        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, id)), "所选角色已存在关联菜单，请先删除关联菜单！");
//        Assert.isTrue(0 == new SysUserRole().selectCount(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, id)), "所选角色已存在关联用户，请先删除关联用户！");
    }

    @Override
    void afterDelete(DevDatasource entity) {
        def dynamicRoutingDataSource = dataSource as DynamicRoutingDataSource
        dynamicRoutingDataSource.removeDataSource(entity.name)
    }

    @Override
    void beforeBatchDelete(List<DevDatasource> entities) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, ids).in(SysRole::getCode, EnumUtil.getFieldValues(DefaultRole.class, "value"))), "所选记录中包含系统默认角色，不能删除！");
//        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, ids)), "所选角色中已存在关联菜单，请先删除关联菜单！");
//        Assert.isTrue(0 == new SysUserRole().selectCount(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getRoleId, ids)), "所选角色中已存在关联用户，请先删除关联用户！");
    }

    @Override
    void afterBatchDelete(List<DevDatasource> entities) {
        def dynamicRoutingDataSource = dataSource as DynamicRoutingDataSource
        entities*.name.each {name ->
            dynamicRoutingDataSource.removeDataSource(name)
        }
    }

    void addDataSource(DevDatasource entity) {
        def dynamicRoutingDataSource = dataSource as DynamicRoutingDataSource
        dynamicRoutingDataSource.removeDataSource(entity.name)
        DataSourceProperty dataSourceProperty = new DataSourceProperty()
        dataSourceProperty.setUsername(entity.getUsername())
        dataSourceProperty.setPassword(entity.getPassword())
        dataSourceProperty.setUrl(entity.getUrl())
        dataSourceProperty.setDriverClassName(entity.getDriverClassName())
        DataSource createDataSource = dataSourceCreator.createDataSource(dataSourceProperty)
        dynamicRoutingDataSource.addDataSource(entity.name, createDataSource)
    }
}

