package com.weasel.modules.sys.util


import com.weasel.modules.sys.entity.SysRole

/**
 * @author weasel
 * @date 2022/3/29 14:45
 * @version 1.0
 */
final class RoleUtil {

    static buildRoles(List<SysRole> roles) {
        roles.findAll {
            !it.parentId
        }.each {
            def children = getChildren(it, roles)
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
    static getChildren(SysRole root, List<SysRole> all) {
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
