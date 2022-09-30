package com.weasel.modules.sys.controller;

import ai.yue.library.base.view.Result;
import com.fhs.core.trans.anno.TransMethodResult;
import com.weasel.common.base.BaseController;
import com.weasel.modules.sys.entity.SysUser;
import com.weasel.modules.sys.service.SysUserService;
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
//@RequestMapping("/sys/user")
@Slf4j
public class SysUserController extends BaseController<SysUser> {
    @Autowired
    SysUserService sysUserService;

    @Override
//    @SaCheckPermission(value = {REQUEST_MAPPING_PREFIX, REQUEST_MAPPING_PREFIX + "===GET"}, mode = SaMode.OR)
    @TransMethodResult
    public Result list() {
        return super.list();
    }

    @Override
    public void beforeDelete(Long id) {

    }
}
