package com.weasel.modules.sys.entity;

import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.weasel.common.base.BaseEntity;
import lombok.Data;

@Data
@Table(value = "sys_user_role", comment = "用户-角色表", excludeFields = {"serialVersionUID", "entityClass"})
public class SysUserRole extends BaseEntity<SysUserRole> {

    @Column(comment = "用户id", notNull = true)
    private Long userId;
    @Column(comment = "角色id", notNull = true)
    private Long roleId;
}
