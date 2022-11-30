package com.weasel.modules.sys.util

import cn.hutool.core.lang.tree.Tree
import cn.hutool.core.lang.tree.TreeNodeConfig
import com.weasel.common.base.TreeEntity
import com.weasel.modules.sys.entity.SysRole

import javax.sound.midi.Soundbank

/**
 * @author weasel
 * @date 2022/3/29 14:45
 * @version 1.0
 */
final class TreeUtil {

    static <T extends TreeEntity> List<Tree<T>> build(List<T> all, def... query) {
//        nodes.findAll {
//            !it.parentId
//        }.each {
//            def children = getChildren(it, nodes)
//            if (children) {
//                it.children = children
//            }
//        }


        def util = cn.hutool.core.lang.tree.TreeUtil
        def tree = util.build(all, null,
                (treeNode, tree) -> {
                    tree.with {
                        id = treeNode.id
                        parentId = treeNode.parentId
                        weight = treeNode.orderNo
                        name = treeNode.name
                    }
                })
        def trees = []
        query && (query = query[0])
        query.each {
            def node = tree.get(0).getNode(it.id)
            trees << node
        }
        trees
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

    static void main(String[] args) {
        def nodes = []
        SysRole node = new SysRole()
        node.with {
            name = 'a'
            id = 1
            parentId = null
            orderNo = 1
        }
        nodes << node
        node = new SysRole()
        node.with {
            name = 'aa'
            id = 11L
            parentId = 1L
            orderNo = 1
        }
        nodes << node
        node = new SysRole()
        node.with {
            name = 'ab'
            id = 12L
            parentId = 1L
            orderNo = 2
        }
        nodes << node

        node = new SysRole()
        node.with {
            name = 'b'
            id = 2L
            parentId = null
            orderNo = 2
        }
        nodes << node
        node = new SysRole()
        node.with {
            name = 'ba'
            id = 21L
            parentId = 2L
            orderNo = 1
        }
        nodes << node
        node = new SysRole()
        node.with {
            name = 'bb'
            id = 22L
            parentId = 2L
            orderNo = 2
        }
        nodes << node

        println nodes
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
//        treeNodeConfig.setWeightKey("orderNo");

        //转换器
        List<Tree<SysRole>> treeNodes = cn.hutool.core.lang.tree.TreeUtil.build(nodes,  null,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getOrderNo());
                    tree.setName(treeNode.getName());
                });
        println treeNodes.size()
        treeNodes.each {
            println it
        }
    }
}
