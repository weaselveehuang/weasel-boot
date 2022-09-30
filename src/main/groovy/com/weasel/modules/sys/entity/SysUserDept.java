package com.weasel.modules.sys.entity;

import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.weasel.common.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/8 9:36
 */
@Data
@Table(value = "sys_user_dept", comment = "用户-部门表", excludeFields = {"serialVersionUID", "entityClass"})
public class SysUserDept extends BaseEntity<SysUserDept> {

    @Column(comment = "用户id", notNull = true)
    @NotNull
    private Long userId;
    @Column(comment = "部门id", notNull = true)
    @NotNull
    private Long deptId;
}
