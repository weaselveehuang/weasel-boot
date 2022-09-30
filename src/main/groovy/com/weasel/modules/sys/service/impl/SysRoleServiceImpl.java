package com.weasel.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.weasel.common.util.ListUtil;
import com.weasel.modules.sys.entity.SysRole;
import com.weasel.modules.sys.entity.SysRoleMenu;
import com.weasel.modules.sys.mapper.SysRoleMapper;
import com.weasel.modules.sys.service.SysRoleMenuService;
import com.weasel.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SysRoleServiceImpl extends MPJBaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleMenu(SysRole entity) {
        List<Long> newMenuIds = entity.getMenu();
        boolean ret = false;
        List<Long> oldMenuIds = new SysRoleMenu().selectList(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, entity.getId())).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        List<Long> needInsert = ListUtil.getDiff(newMenuIds, oldMenuIds);
        List<Long> needDelete = ListUtil.getDiff(oldMenuIds, newMenuIds);
        if (CollUtil.isNotEmpty(needInsert)) {
            List<SysRoleMenu> sysRoleMenus = needInsert.stream().collect(ArrayList::new, (list, menuId) -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(entity.getId());
                sysRoleMenu.setMenuId(menuId);
                list.add(sysRoleMenu);
            }, ArrayList::addAll);
            ret = sysRoleMenuService.saveBatch(sysRoleMenus);
        }
        if (CollUtil.isNotEmpty(needDelete)) {
            ret = sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, entity.getId()).in(SysRoleMenu::getMenuId, needDelete));
        }
        return ret;
    }

}




