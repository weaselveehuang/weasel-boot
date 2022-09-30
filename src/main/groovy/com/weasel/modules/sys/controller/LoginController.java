package com.weasel.modules.sys.controller;

import ai.yue.library.base.view.R;
import ai.yue.library.base.view.Result;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weasel.common.consts.Consts;
import com.weasel.config.StpInterfaceImpl;
import com.weasel.modules.sys.entity.SysUser;
import com.weasel.modules.sys.service.SysMenuService;
import com.weasel.modules.sys.util.MenuUtil;
import com.weasel.modules.sys.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/31 9:27
 */
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    StpInterfaceImpl stpInterface;

    @PostMapping("login")
    public Result login(SysUser sysUser) {
        SysUser one = new SysUser().selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, sysUser.getUsername()));
        Assert.notNull(one, "用户名或密码不正确");
//        one.setPassword(PasswordUtil.encrypt(one.getSalt(), sysUser.getPassword()));
//        sysUserService.updateById(one);
        Assert.isTrue(StrUtil.equals(PasswordUtil.encrypt(one.getSalt(), sysUser.getPassword()), one.getPassword()), "用户名或密码不正确");
        StpUtil.login(one.getId());
        StpUtil.getSession().set(Consts.Session.LOGIN_USER, one);
        return R.success(MapUtil.builder().put("userId", StpUtil.getLoginId()).put("token", StpUtil.getTokenValue()).build());
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader String authorization) {
        Object loginIdByToken = StpUtil.getLoginIdByToken(authorization);
        SaSession session = StpUtil.getSessionByLoginId(loginIdByToken);
        SysUser sysUser = session.getModel(Consts.Session.LOGIN_USER, SysUser.class);
        return R.success(sysUser);
    }

    @GetMapping("/getPermCode")
    public Result getPermCode() {
        return R.success(StpUtil.getPermissionList());
    }

    @GetMapping("/getPermObj")
    public Result getPermObj() {
        return R.success(stpInterface.getPermissionMenuList(StpUtil.getLoginId(), StpUtil.getLoginType()));
    }

    @GetMapping("/logout")
    public Result logout() {
        StpUtil.logout();
        return R.success();
    }

    @GetMapping("/getMenuList")
    public Result getMenuList() {
        return R.success(MenuUtil.buildRoutes(stpInterface.getMenuList()));
    }

    @GetMapping("/isSuperAdmin/{id}")
    public Result isSuperAdmin(@PathVariable Long id) {
        return R.success(stpInterface.isSuperAdmin(id));
    }
}
