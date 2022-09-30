package com.weasel.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.toolkit.MPJWrappers;
import com.weasel.modules.sys.entity.*;
import com.weasel.modules.sys.enums.DataScope;
import com.weasel.modules.sys.enums.DefaultRole;
import com.weasel.modules.sys.enums.MenuType;
import com.weasel.modules.sys.service.SysMenuService;
import com.weasel.modules.sys.service.SysRoleService;
import com.weasel.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysRoleService sysRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<SysMenu> sysMenus;
        if (isSuperAdmin(loginId)) {
            sysMenus = sysMenuService.list(
                    Wrappers.<SysMenu>lambdaQuery()
                            .ne(SysMenu::getPermission, "")
                            .isNotNull(SysMenu::getPermission)
            );
        } else {
            sysMenus = sysMenuService.selectJoinList(SysMenu.class, MPJWrappers.<SysMenu>lambdaJoin()
                    .select(SysMenu::getPermission)
                    .leftJoin(SysRoleMenu.class, SysRoleMenu::getMenuId, SysMenu::getId)
                    .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRoleMenu::getRoleId)
                    .leftJoin(SysUser.class, SysUser::getId, SysUserRole::getUserId)
                    .eq(SysUser::getId, loginId)
                    .ne(SysMenu::getPermission, "")
                    .isNotNull(SysMenu::getPermission)
            );
        }
        return sysMenus.stream().filter(Objects::nonNull).map(SysMenu::getPermission).distinct().collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return sysRoleService.selectJoinList(SysRole.class,
                MPJWrappers.<SysRole>lambdaJoin().select(SysRole::getCode)
                        .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRole::getId)
                        .leftJoin(SysUser.class, SysUser::getId, SysUserRole::getUserId)
                        .eq(SysUser::getId, loginId)
        ).stream().map(SysRole::getCode).collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的权限菜单实体集合
     */
    public List<SysMenu> getPermissionMenuList(Object loginId, String loginType) {
        List<SysMenu> sysMenus;
        if (isSuperAdmin(loginId)) {
            sysMenus = sysMenuService.list(
                    Wrappers.<SysMenu>lambdaQuery()
                            .ne(SysMenu::getPermission, "")
                            .isNotNull(SysMenu::getPermission)
            );
        } else {
            sysMenus = sysMenuService.selectJoinList(SysMenu.class, MPJWrappers.<SysMenu>lambdaJoin()
                    .selectAll(SysMenu.class)
                    .leftJoin(SysRoleMenu.class, SysRoleMenu::getMenuId, SysMenu::getId)
                    .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRoleMenu::getRoleId)
                    .leftJoin(SysUser.class, SysUser::getId, SysUserRole::getUserId)
                    .eq(SysUser::getId, loginId)
                    .ne(SysMenu::getPermission, "")
                    .isNotNull(SysMenu::getPermission)
            );
        }
        return sysMenus.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 返回数据权限
     */
    public List<DataScope> getDataScopes(Object loginId, String loginType) {
        return sysRoleService.selectJoinList(SysRole.class,
                        MPJWrappers.<SysRole>lambdaJoin().select(SysRole::getDataScope)
                                .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRole::getId)
                                .leftJoin(SysUser.class, SysUser::getId, SysUserRole::getUserId)
                                .eq(SysUser::getId, loginId)
                ).stream().map(SysRole::getDataScope)
                .collect(ArrayList::new, (list, dataScope) -> list.add(dataScope), ArrayList::addAll);
    }

    /**
     * 返回数据权限
     */
    public List<DataScope> getDataScopes() {
        return getDataScopes(StpUtil.getLoginId(), StpUtil.getLoginType());
    }

    /**
     * 返回一个账号所拥有的菜单集合
     */
    public List<SysMenu> getMenuList(Object loginId, String loginType) {
        List<SysMenu> sysMenus;
        if (isSuperAdmin(loginId)) {
            sysMenus = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getType, MenuType.BUTTON));
        } else {
            sysMenus = sysMenuService.selectJoinList(SysMenu.class, MPJWrappers.<SysMenu>lambdaJoin()
                            .selectAll(SysMenu.class)
                            .leftJoin(SysRoleMenu.class, SysRoleMenu::getMenuId, SysMenu::getId)
//                        .leftJoin(SysRoleMenu.class, on -> on
//                                .eq(SysRoleMenu::getMenuId, SysMenu::getId)
//                                .eq(SysRoleMenu::getDeleted, false))
                            .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRoleMenu::getRoleId)
//                        .leftJoin(SysUserRole.class, on -> on
//                                .eq(SysUserRole::getRoleId, SysRoleMenu::getRoleId)
//                                .eq(SysUserRole::getDeleted, false))
                            .leftJoin(SysUser.class, SysUser::getId, SysUserRole::getUserId)
//                        .leftJoin(SysUser.class, on -> on
//                                .eq(SysUser::getId, SysUserRole::getUserId)
//                                .eq(SysUser::getDeleted, false))
                            .ne(SysMenu::getType, MenuType.BUTTON)
                            .eq(SysUser::getId, StpUtil.getLoginId())
                            .orderByAsc(SysMenu::getOrderNo)
                            .groupBy(SysMenu::getId)
            );
            sysMenus = sysMenus.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        }
        return sysMenus.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的菜单集合
     */
    public List<SysMenu> getMenuList() {
        return getMenuList(StpUtil.getLoginId(), StpUtil.getLoginType());
    }

    /**
     * 是否超级管理员，超级管理员默认拥有所有权限
     */
    public boolean isSuperAdmin(Object loginId) {
        return StpUtil.getRoleList(loginId).contains(DefaultRole.SUPER_ADMIN.getValue());
    }

    /**
     * 是否超级管理员，超级管理员默认拥有所有权限
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin(StpUtil.getLoginId());
    }
}
