package com.weasel.modules.sys.controller;

import ai.yue.library.base.view.Result;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weasel.common.base.BaseController;
import com.weasel.modules.sys.entity.SysDept;
import com.weasel.modules.sys.entity.SysUserDept;
import com.weasel.modules.sys.service.SysDeptService;
import com.weasel.modules.sys.util.DeptUtil;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/24 17:35
 */
//@RestController
//@RequestMapping("/sys/dept")
@Slf4j
class SysDeptController extends BaseController<SysDept> {
    @Autowired
    SysDeptService sysDeptService;

    @Override
    public Result list() {
        Result success = super.list();
        success.setData(DeptUtil.buildDepts(getList(buildSearchParams())));
        return success;
    }

    @Override
    public void beforeDelete(Long id) {
        Assert.isTrue(0 == new SysDept().selectCount(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getParentId, id)), "已存在关联子部门，请先删除关联子部门！");
        Assert.isTrue(0 == new SysUserDept().selectCount(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getDeptId, id)), "已存在关联角色用户，请先删除关联用户！");
    }
}
