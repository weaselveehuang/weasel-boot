package com.weasel.modules.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.DbIgnore;
import com.ejlchina.searcher.bean.SearchBean;
import com.ejlchina.searcher.operator.Contain;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.tangzc.mpe.actable.annotation.Unique;
import com.weasel.common.base.BaseEntity;
import com.weasel.common.consts.Consts;
import com.weasel.modules.sys.vo.ComponentVo;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
@Table(value = "sys_user", comment = "用户表", excludeFields = {"serialVersionUID", "entityClass"})
@SearchBean(tables = "sys_user u " +
        "left join sys_user_dept ud on u.id=ud.user_id left join sys_dept d on d.id=ud.dept_id " +
        "left join sys_user_role ur on u.id=ur.user_id left join sys_role r on r.id=ur.role_id ", distinct = true, autoMapTo = "u")
public class SysUser extends BaseEntity<SysUser> {

    @Column(comment = "账号", notNull = true)
    @Unique(columns = {"tenant_id", "deleted", "username"})
    @NotBlank(message = "账号不能为空!")
    private String username;
    @Column(comment = "真实姓名")
    @DbField(onlyOn = Contain.class)
    private String realName;
    @Column(comment = "密码", notNull = true)
    @NotBlank(message = "密码不能为空!")
    @JSONField(serialize = false)
    private String password;
    @Column(comment = "md5密码盐", notNull = true)
    @JSONField(serialize = false)
    private String salt;
    @Column(comment = "头像", length = 2000)
    private String avatar;
    @Column(comment = "生日")
    @Past
    private Date birthday;
    @Column(comment = "性别(0-默认未知,1-男,2-女)")
    @Trans(type = TransType.DICTIONARY, key = "sex", ref = "sexName")
    private Integer sex;
    @ExcelIgnore
//    @ExcelProperty("性别")
    @TableField(exist = false)
    @DbIgnore
    private String sexName;
    @Column(comment = "电子邮件")
    @Email(message = "邮箱格式不正确！", groups = {Consts.ValidateGroup.UPDATE.class, Consts.ValidateGroup.SAVE.class})
    private String email;
    @Column(comment = "手机号码")
    @Pattern(regexp = "(?:0|86|\\+86)?1[3-9]\\d{9}", message = "手机号码格式不正确！", groups = {Consts.ValidateGroup.UPDATE.class, Consts.ValidateGroup.SAVE.class})
    private String phone;
    @Column(comment = "是否禁用", notNull = true, defaultValue = "0")
    private boolean disabled;
    @Column(comment = "首页", length = 2000)
    private String homePath;

    @TableField(exist = false)
    @DbIgnore
    private List<Long> roleIds;

    @TableField(exist = false)
    @DbIgnore
    private List<ComponentVo> depts;

    @TableField(exist = false)
    @DbIgnore
    private Boolean isSuperAdmin;

    @TableField(exist = false)
    @DbField(value = "d.id")
    private Long deptId;

    @TableField(exist = false)
    @DbField(value = "r.id")
    private Long roleId;

}
