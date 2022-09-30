package com.weasel.modules.sys.entity;

import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.weasel.common.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Table(value = "sys_role_menu", comment = "角色-菜单表", excludeFields = {"serialVersionUID", "entityClass"})
public class SysRoleMenu extends BaseEntity<SysRoleMenu> {

    @Column(comment = "角色id", notNull = true)
    @NotNull
    private Long roleId;
    @Column(comment = "菜单id", notNull = true)
    @NotNull
    private Long menuId;
}
