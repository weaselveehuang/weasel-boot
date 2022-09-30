package com.weasel.modules.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.weasel.modules.sys.entity.SysRole;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
public interface SysRoleService extends MPJBaseService<SysRole> {

    @Transactional(rollbackFor = Exception.class)
    boolean saveRoleMenu(SysRole entity);
}
