package com.weasel.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.weasel.common.util.ListUtil;
import com.weasel.config.StpInterfaceImpl;
import com.weasel.modules.sys.entity.SysUser;
import com.weasel.modules.sys.entity.SysUserDept;
import com.weasel.modules.sys.entity.SysUserRole;
import com.weasel.modules.sys.mapper.SysUserMapper;
import com.weasel.modules.sys.service.SysUserDeptService;
import com.weasel.modules.sys.service.SysUserRoleService;
import com.weasel.modules.sys.service.SysUserService;
import com.weasel.modules.sys.vo.ComponentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SysUserServiceImpl extends MPJBaseServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    @Autowired
    SysUserDeptService sysUserDeptService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    StpInterfaceImpl stpInterface;

    @Override
    public SysUser getById(Serializable id) {
        SysUser entity = super.getById(id);
//        List<SysDept> sysDepts = sysDeptService.selectJoinList(SysDept.class,
//                MPJWrappers.<SysDept>lambdaJoin().select(SysDept::getName, SysDept::getId)
//                        .leftJoin(SysUserDept.class, SysUserDept::getDeptId, SysDept::getId)
//                        .eq(SysUserDept::getUserId, id)
//        );
//        List<ComponentVo> depts = sysDepts.stream().collect(ArrayList::new, (list, dept) -> {
//            ComponentVo componentVo = new ComponentVo();
//            componentVo.setValue(dept.getId());
//            componentVo.setLabel(dept.getName());
//            componentVo.setDisabled(dept.getDisabled());
//            list.add(componentVo);
//        }, ArrayList::addAll);

        List<SysUserDept> sysUserDepts = sysUserDeptService.list(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getUserId, id));
        List<ComponentVo> depts = sysUserDepts.stream().collect(ArrayList::new, (list, dept) -> {
            ComponentVo componentVo = new ComponentVo();
            componentVo.setValue(dept.getDeptId());
            list.add(componentVo);
        }, ArrayList::addAll);
        entity.setDepts(depts);

        List<SysUserRole> sysUserRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, id));
        List<Long> roles = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        entity.setRoleIds(roles);

        entity.setIsSuperAdmin(stpInterface.isSuperAdmin(id));
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysUser entity) {
        List<Long> newDeptIds = entity.getDepts().stream().map(ComponentVo::getValue).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(newDeptIds)) {
            List<SysUserDept> sysUserDepts = newDeptIds.stream().collect(ArrayList::new, (list, deptId) -> {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setUserId(entity.getId());
                sysUserDept.setDeptId(deptId);
                list.add(sysUserDept);
            }, ArrayList::addAll);
            sysUserDeptService.saveBatch(sysUserDepts);
        }

        List<Long> newRoleIds = entity.getRoleIds();
        if (CollUtil.isNotEmpty(newRoleIds)) {
            List<SysUserRole> sysUserRoles = newRoleIds.stream().collect(ArrayList::new, (list, deptId) -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(entity.getId());
                sysUserRole.setRoleId(deptId);
                list.add(sysUserRole);
            }, ArrayList::addAll);
            sysUserRoleService.saveBatch(sysUserRoles);
        }
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(SysUser entity) {
        List<Long> newDeptIds = CollUtil.getFieldValues(entity.getDepts(), "value", Long.class);
        List<Long> oldDeptIds = new SysUserDept().selectList(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getUserId, entity.getId())).stream().map(SysUserDept::getDeptId).collect(Collectors.toList());
        List<Long> needInsertDept = ListUtil.getDiff(newDeptIds, oldDeptIds);
        List<Long> needDeleteDept = ListUtil.getDiff(oldDeptIds, newDeptIds);
        if (CollUtil.isNotEmpty(needInsertDept)) {
            List<SysUserDept> sysUserDepts = needInsertDept.stream().collect(ArrayList::new, (list, deptId) -> {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setUserId(entity.getId());
                sysUserDept.setDeptId(deptId);
                list.add(sysUserDept);
            }, ArrayList::addAll);
            sysUserDeptService.saveBatch(sysUserDepts);
        }
        if (CollUtil.isNotEmpty(needDeleteDept)) {
            sysUserDeptService.remove(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getUserId, entity.getId()).in(SysUserDept::getDeptId, needDeleteDept));
        }

        List<Long> newRoleIds = entity.getRoleIds();
        List<Long> oldRoleIds = new SysUserRole().selectList(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, entity.getId())).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<Long> needInsertRole = ListUtil.getDiff(newRoleIds, oldRoleIds);
        List<Long> needDeleteRole = ListUtil.getDiff(oldRoleIds, newRoleIds);
        if (CollUtil.isNotEmpty(needInsertRole)) {
            List<SysUserRole> sysUserRoles = needInsertRole.stream().collect(ArrayList::new, (list, deptId) -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(entity.getId());
                sysUserRole.setRoleId(deptId);
                list.add(sysUserRole);
            }, ArrayList::addAll);
            sysUserRoleService.saveBatch(sysUserRoles);
        }
        if (CollUtil.isNotEmpty(needDeleteRole)) {
            sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, entity.getId()).in(SysUserRole::getRoleId, needDeleteRole));
        }
        return super.updateById(entity);
    }
}
