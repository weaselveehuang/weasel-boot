package com.weasel.modules.sys.util


import com.weasel.modules.sys.entity.SysMenu
import com.weasel.modules.sys.enums.MenuType

/**
 * @author weasel
 * @date 2022/3/29 14:45
 * @version 1.0
 */
final class MenuUtil {

    static buildRoute(SysMenu it) {
        def route = [:]
        route << [
                name      : it?.id,
                path      : it?.path,
                component : it?.component,
                id        : it?.id,
                createTime: it?.createTime,
                permission: it?.permission,
                children  : it?.children,
                redirect  : it?.redirect,
                meta      : [
                        orderNo        : it?.orderNo,
                        title          : it?.title,
                        dynamicLevel   : it?.dynamicLevel,
                        ignoreKeepAlive: it?.ignoreKeepAlive,
                        icon           : it?.icon,
                        frameSrc       : it?.frameSrc,
                        hideBreadcrumb : it?.hideBreadcrumb,
                        hideMenu       : it?.hideMenu,
                        hideTab        : it?.hideTab,
                        isLink         : it?.isLink,
                        ignoreRoute         : it?.ignoreRoute,
                        redirect         : it?.redirect,
                ]
        ]
        route
    }

    static buildRoutes(List<SysMenu> menus) {
        def routes = []
        menus.findAll {
            it.type == MenuType.CATALOG
        }.each {
            it.children = getChildren4Route(it, menus)
            routes << buildRoute(it)
        }
        routes
    }

    /**
     * 递归查询子节点
     *
     * @param root 根节点
     * @param all 所有节点
     * @return 根节点信息
     */
    static getChildren4Route(SysMenu root, List<SysMenu> all) {
        def routes = []
        all.findAll {
            it.type == MenuType.MENU && it.parentId == root.id
        }.each {
            it.children = getChildren4Route(it, all)
            routes << buildRoute(it)
        }
        routes
    }

    static buildMenus(List<SysMenu> menus) {
        menus.findAll {
            it.type == MenuType.CATALOG
        }.each {
            def children = getChildren(it, menus)
            if (children) {
                it.children = children
            }
        }
    }

    /**
     * 递归查询子节点
     *
     * @param root 根节点
     * @param all 所有节点
     * @return 根节点信息
     */
    static getChildren(SysMenu root, List<SysMenu> all) {
        all.findAll {
            it.parentId == root.id
        }.each {
            def children = getChildren(it, all)
            if (children) {
                it.children = children
            }
        }
    }
}
