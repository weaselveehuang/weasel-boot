//package com.weasel
//
//import cn.hutool.core.bean.BeanUtil
//import cn.hutool.core.util.RandomUtil
//import cn.hutool.crypto.SecureUtil
//import cn.hutool.http.HttpUtil
//import com.weasel.modules.sys.entity.SysDept
//import com.weasel.modules.sys.entity.SysMenu
//import com.weasel.modules.sys.entity.SysRole
//import com.weasel.modules.sys.entity.SysRoleMenu
//import com.weasel.modules.sys.entity.SysUser
//import com.weasel.modules.sys.entity.SysUserRole
//import com.weasel.modules.sys.service.SysMenuService
//import com.weasel.modules.sys.service.SysRoleMenuService
//import com.weasel.modules.sys.service.SysRoleService
//import com.weasel.modules.sys.service.SysUserService
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//
//import java.time.LocalDateTime
//
///**
// * @author weasel
// * @date 2022/3/29 15:32
// * @version 1.0
// */
//@SpringBootTest
//class InitData {
//
//    @Autowired
//    SysUserService sysUserService
//    @Autowired
//    SysRoleService sysRoleService
//    @Autowired
//    SysMenuService sysMenuService
//    @Test
//    void menu() {
//
//        def menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 1
//            disabled = false
//            parentId = null
//            path = '/dashboard'
//            component = 'LAYOUT'
//            orderNo = 1
//            title = 'Dashboard'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:layers-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 1
//            disabled = false
//            parentId = null
//            path = '/system'
//            component = 'LAYOUT'
//            orderNo = 2
//            title = '系统管理'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 2
//            disabled = false
//            parentId = '2'
//            path = 'account'
//            component = '/demo/system/account/index'
//            orderNo = 1
//            title = '账号管理'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 2
//            disabled = false
//            parentId = '2'
//            path = 'account_detail/:id'
//            component = '/demo/system/account/AccountDetail'
//            orderNo = 2
//            title = '账号详情'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 2
//            disabled = false
//            parentId = '2'
//            path = 'role'
//            component = '/demo/system/role/index'
//            orderNo = 3
//            title = 'routes.demo.system.role'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 2
//            disabled = false
//            parentId = '2'
//            path = 'menu'
//            component = '/demo/system/menu/index'
//            orderNo = 4
//            title = 'routes.demo.system.menu'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 2
//            disabled = false
//            parentId = '2'
//            path = 'dept'
//            component = '/demo/system/menu/dept'
//            orderNo = 5
//            title = 'routes.demo.system.dept'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//        menu = new SysMenu()
//        menu.with {
//            permission = null
//            type = 2
//            disabled = false
//            parentId = '2'
//            path = 'changePassword'
//            component = '/demo/system/password/index'
//            orderNo = 5
//            title = 'routes.demo.system.password'
//            dynamicLevel = 1
//            ignoreKeepAlive = true
//            icon = 'ion:settings-outline'
//            frameSrc = null
//            hideBreadcrumb = false
//            hideMenu = false
//            isLink = false
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/menu", BeanUtil.beanToMap(menu))
//
//    }
//
//    @Test
//    void role() {
//
//        def role = new SysRole()
//        role.with {
//            name = '超级管理员'
//            code = 'sa'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//        }
//        HttpUtil.post("http://localhost:3000/sys/role", BeanUtil.beanToMap(role))
//
//        role = new SysRole()
//        role.with {
//            name = '管理员'
//            code = 'admin'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//        }
//        HttpUtil.post("http://localhost:3000/sys/role", BeanUtil.beanToMap(role))
//
//        role = new SysRole()
//        role.with {
//            name = '普通用户'
//            code = 'user'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//        }
//        HttpUtil.post("http://localhost:3000/sys/role", BeanUtil.beanToMap(role))
//    }
//
//    @Test
//    void user() {
//        def user = new SysUser()
//        user.with {
//            username = 'admin'
//            realName = 'admin'
//            salt = RandomUtil.randomString(6)
//            password = com.weasel.modules.sys.util.PasswordUtil.encrypt("sfdsf", "123456")
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//        }
//        HttpUtil.post("http://localhost:3000/sys/user", BeanUtil.beanToMap(user))
//
//        for (i in 0..<100) {
//            user = new SysUser()
//            user.with {
//                username = RandomUtil.randomString(6)
//                realName = RandomUtil.randomString(6)
//                salt = RandomUtil.randomString(6)
//                password = com.weasel.modules.sys.util.PasswordUtil.encrypt("sfdsf", "123456")
//                description = null
//                deleted = false
//                version = 0
//                createTime = LocalDateTime.now()
//                createBy = 0
//            }
//            HttpUtil.post("http://localhost:3000/sys/user", BeanUtil.beanToMap(user))
//        }
//    }
//
//    @Test
//    void userRole() {
//        def userIds = sysUserService.list()*.id
//        def roleIds = sysRoleService.list()*.id
//
//        for (i in 0..<100) {
//            def userRole = new SysUserRole()
//            userRole.with {
//                userId = RandomUtil.randomEle(userIds,100)
//                roleId = RandomUtil.randomEle(roleIds,100)
//                description = null
//                deleted = false
//                version = 0
//                createTime = LocalDateTime.now()
//                createBy = 0
//            }
//            HttpUtil.post("http://localhost:3000/sys/userRole", BeanUtil.beanToMap(userRole))
//        }
//    }
//
//    @Test
//    void roleMenu() {
//        def menuIds = sysMenuService.list()*.id
//        def roleIds = sysRoleService.list()*.id
//
//        for (i in 0..<100) {
//            def roleMenu = new SysRoleMenu()
//            roleMenu.with {
//                menuId = RandomUtil.randomEle(menuIds,100)
//                roleId = RandomUtil.randomEle(roleIds,100)
//                description = null
//                deleted = false
//                version = 0
//                createTime = LocalDateTime.now()
//                createBy = 0
//            }
//            HttpUtil.post("http://localhost:3000/sys/roleMenu", BeanUtil.beanToMap(roleMenu))
//        }
//    }
//
//    @Test
//    void dept() {
//
//        def dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 1
//            name = '部门1'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        def p = HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//        println p
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 2
//            name = '部门2'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 3
//            name = '部门3'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 4
//            name = '部门4'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 5
//            name = '部门5'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 6
//            name = '部门6'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 7
//            name = '部门7'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//        dept = new SysDept()
//        dept.with {
//            disabled = false
//            parentId = null
//            orderNo = 8
//            name = '部门8'
//            description = null
//            deleted = false
//            version = 0
//            createTime = LocalDateTime.now()
//            createBy = 0
//
//        }
//        HttpUtil.post("http://localhost:3000/sys/dept", BeanUtil.beanToMap(dept))
//
//    }
//}
