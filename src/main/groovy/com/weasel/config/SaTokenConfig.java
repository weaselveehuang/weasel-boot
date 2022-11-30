package com.weasel.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weasel.modules.sys.entity.SysMenu;
import com.weasel.modules.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Autowired
    WeaselProperties weaselProperties;
    @Autowired
    SysMenuService sysMenuService;

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaInterceptor(
                        handle -> {
//                    SaRouter.match("/getUserInfo", "/getMenuList", "/getPermCode")
//                            .check(r -> StpUtil.checkLogin()).stop()
//                    ;
//                    SaRouter.match(SaHttpMethod.POST)
//                            .match("/**")
//                            .check(r -> StpUtil.checkPermission(req.getRequestPath() + "===" + req.getMethod() + "===" + "TOOLBAR"))
//                    ;
//                    SaRouter.match(SaHttpMethod.GET)
//                            .match("/**/{id}")
//                            .check(r -> StpUtil.checkPermissionOr(req.getRequestPath() + "===" + "INLINE", req.getRequestPath() + "===" + req.getMethod() + "===" + "INLINE"))
//                            .stop()
//                    ;
//                    SaRouter.match(SaHttpMethod.GET)
//                            .check(r -> StpUtil.checkPermissionOr(req.getRequestPath(), req.getRequestPath() + "===" + "INLINE", req.getRequestPath() + "===" + req.getMethod() + "===" + "INLINE"))
//                    ;
//                    SaRouter.match(SaHttpMethod.PUT)
//                            .match("/**/{id}")
//                            .check(r -> StpUtil.checkPermission(req.getRequestPath() + "===" + req.getMethod() + "===" + "INLINE"))
//                    ;
//                    SaRouter.match(SaHttpMethod.DELETE)
//                            .match("/**/{id}")
//                            .check(r -> StpUtil.checkPermission(req.getRequestPath() + "===" + req.getMethod() + "===" + "INLINE"))
//                    ;
//                    SaRouter.match(SaHttpMethod.DELETE)
//                            .match("/**/batch")
//                            .check(r -> StpUtil.checkPermission(req.getRequestPath() + "===" + req.getMethod() + "===" + "BATCH"))
//                    ;

                            // 根据路由划分模块，不同模块不同鉴权
//                            SaRouter.match("/sys/role", r -> StpUtil.checkPermission("/sys/role"));

                            List<SysMenu> sysMenuList = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().select(SysMenu::getPermission).isNotNull(SysMenu::getPermission).ne(SysMenu::getPermission, ""));

                            sysMenuList.forEach(sysMenu -> {
                                String permission = sysMenu.getPermission();
                                List<String> list = StrUtil.splitTrim(permission, "===");
                                if (list.size() == 1) {
                                    SaRouter.match(list.get(0), r -> StpUtil.checkPermission(list.get(0)));
                                }
                                if (list.size() == 2) {
                                    SaRouter.match(list.get(0), r -> StpUtil.checkPermission(list.get(0) + "===" + list.get(1)));
                                }
                                if (list.size() == 3) {
                                    SaRouter.match(EnumUtil.fromString(SaHttpMethod.class, list.get(1)))
                                            .match(list.get(0))
                                            .check(r -> StpUtil.checkPermission(list.get(0) + "===" + list.get(1) + "===" + list.get(2)));
                                }
                            });
                        }
                ))
                .addPathPatterns("/**")
                .excludePathPatterns(weaselProperties.getExcludePath());
    }
}
