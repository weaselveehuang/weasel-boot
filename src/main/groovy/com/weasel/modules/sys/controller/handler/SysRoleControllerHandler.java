//package com.weasel.modules.sys.controller.handler;
//
//import ai.yue.library.base.view.R;
//import ai.yue.library.base.view.Result;
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.lang.Assert;
//import cn.hutool.core.util.EnumUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.weasel.common.base.CommonControllerHandler;
//import com.weasel.modules.sys.entity.SysRole;
//import com.weasel.modules.sys.entity.SysRoleMenu;
//import com.weasel.modules.sys.entity.SysUserRole;
//import com.weasel.modules.sys.enums.DefaultRole;
//import com.weasel.modules.sys.service.SysRoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author weasel
// * @date 2022/7/25 9:45
// * @version 1.0
// */
////@RestController
////@RequestMapping("/sys/role")
//public class SysRoleControllerHandler implements CommonControllerHandler<SysRole> {
//    @Autowired
//    SysRoleService sysRoleService;
//
////    @Override
////    public Result list() {
////        Result success = super.list();
////        success.setData(RoleUtil.buildRoles(getList(buildSearchParams())));
////        return success;
////    }
//
//    @Override
//    public void beforeSave(SysRole entity) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, entity.getName())), "已存在相同名称记录！");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, entity.getCode())), "已存在相同值记录！");
//    }
//
//    @Override
//    public void beforeUpdate(SysRole entity) {
//        Assert.isFalse(EnumUtil.contains(DefaultRole.class, entity.getCode()), "系统默认角色不允许编辑！");
//        CommonControllerHandler.super.beforeUpdate(entity);
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, entity.getId()).eq(SysRole::getName, entity.getName())), "已存在相同名称记录！");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, entity.getId()).eq(SysRole::getCode, entity.getCode())), "已存在编码名称记录！");
//    }
//
//    @Override
//    public void beforeDelete(SysRole entity) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, entity.getId()).in(SysRole::getCode, EnumUtil.getFieldValues(DefaultRole.class, "value"))), "所选角色为系统默认角色，不能删除！");
//        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, entity.getId())), "所选角色已存在关联菜单，请先删除关联菜单！");
//        Assert.isTrue(0 == new SysUserRole().selectCount(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, entity.getId())), "所选角色已存在关联用户，请先删除关联用户！");
//    }
//
//    @Override
//    public void beforeBatchDelete(List<SysRole> entities) {
//        List<Object> ids = CollUtil.getFieldValues(entities, new SysRole().getPkey().toString());
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, ids).in(SysRole::getCode, EnumUtil.getFieldValues(DefaultRole.class, "value"))), "所选记录中包含系统默认角色，不能删除！");
//        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, ids)), "所选角色中已存在关联菜单，请先删除关联菜单！");
//        Assert.isTrue(0 == new SysUserRole().selectCount(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getRoleId, ids)), "所选角色中已存在关联用户，请先删除关联用户！");
//    }
//
//    @GetMapping("/menus/{id}")
//    public Result getMenus(@PathVariable Long id) {
//        SysRole entity = new SysRole();
//        entity.setId(id);
//        List<SysRoleMenu> roleMenus = new SysRoleMenu().selectList(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, id));
//        entity.setMenu(roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
//        return R.success(entity);
//    }
//
//    @PostMapping("/menus")
//    @SaCheckPermission("/sys/role/menus/{id}===POST===INLINE")
//    public Result saveMenus(SysRole entity) {
//        return R.success(sysRoleService.saveRoleMenu(entity));
//
//    }
//}
