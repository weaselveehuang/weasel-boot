package com.weasel.modules.sys.controller;

import com.weasel.common.base.BaseController;
import com.weasel.modules.sys.entity.SysConfig;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/24 17:35
 */
@RestController
//@RequestMapping("/sys/config")
@Slf4j
public class SysConfigController extends BaseController<SysConfig> {

    @Override
    public void beforeDelete(Long id) {

    }
}
