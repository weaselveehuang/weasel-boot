package com.weasel.modules.sys.controller;

import ai.yue.library.base.view.Result;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weasel.common.base.BaseController;
import com.weasel.modules.sys.entity.SysMenu;
import com.weasel.modules.sys.entity.SysRoleMenu;
import com.weasel.modules.sys.enums.MenuType;
import com.weasel.modules.sys.util.MenuUtil;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/24 17:35
 */
@RestController
@RequestMapping("/sys/menu")
@Slf4j
class SysMenuController extends BaseController<SysMenu> {

    @Override
    public Result list() {
        Result success = super.list();
        success.setData(MenuUtil.buildMenus(getList(buildSearchParams())));
        return success;
    }

    @Override
    public void beforeSave(SysMenu entity) {
        setPermission(entity);
//        Assert.isTrue(0 == new SysMenu().selectCount(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, entity.getParentId()).eq(SysMenu::getTitle, entity.getTitle())), "已存在相同名称记录！");
    }

    @Override
    public void beforeUpdate(SysMenu entity) {
        super.beforeUpdate(entity);
        setPermission(entity);
//        Assert.isTrue(0 == new SysMenu().selectCount(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getId, entity.getId()).eq(SysMenu::getParentId, entity.getParentId()).eq(SysMenu::getTitle, entity.getTitle())), "已存在相同名称记录！");
    }

    private void setPermission(SysMenu entity) {
        if (entity.getType() == MenuType.BUTTON) {
            Assert.notNull(entity.getParentId(), "上级菜单不能为空！");
            SysMenu parent = service.getById(entity.getParentId());
            String parentPermission = parent.getPermission();
            Assert.notNull(parentPermission, "上级菜单权限标识不能为空！");
            entity.setPermission(StrUtil.addPrefixIfNot(StrUtil.removeSuffix(parentPermission, "/"), "/") +  StrUtil.addPrefixIfNot(entity.getBtnAction(), "/") + "===" + entity.getBtnPosition());
        }
    }

    @Override
    public void beforeDelete(Long id) {
        Assert.isTrue(0 == new SysMenu().selectCount(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, id)), "已存在关联子菜单，请先删除关联子菜单！");
        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, id)), "已存在关联角色，请先删除关联角色！");
    }
}
