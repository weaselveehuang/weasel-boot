package com.weasel.modules.sys.util

import com.weasel.common.base.TreeEntity

/**
 * @author weasel
 * @date 2022/3/29 14:45
 * @version 1.0
 */
final class TreeUtil {

    static <T extends TreeEntity> List<T> build(List<T> nodes) {
        nodes.findAll {
            !it.parentId
        }.each {
            def children = getChildren(it, nodes)
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
    static <T extends TreeEntity> List<T> getChildren(T root, List<T> all) {
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
