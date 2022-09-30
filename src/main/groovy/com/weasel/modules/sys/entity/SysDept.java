package com.weasel.modules.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.DbIgnore;
import com.ejlchina.searcher.operator.Contain;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.tangzc.mpe.actable.annotation.Unique;
import com.weasel.annotation.RefTable;
import com.weasel.annotation.RefTables;
import com.weasel.common.base.TreeEntity;
import com.weasel.common.base.excel.convertor.BooleanStringConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Table(value = "sys_dept", comment = "部门表", excludeFields = {"serialVersionUID", "entityClass"})
@RefTables({@RefTable(entityClass = SysDept.class, selfColumn = "id", refColumn = "parent_id", erroMsg = "所选部门中已存在关联子部门，请先删除关联子部门！")
        ,@RefTable(entityClass = SysUserDept.class, selfColumn = "id", refColumn = "dept_id", erroMsg = "所选部门中已存在关联用户，请先删除关联用户！")})
public class SysDept extends TreeEntity<SysDept> {
    @Column(comment = "名称", length = 500, notNull = true)
    @Unique(columns = {"tenant_id", "deleted", "parent_id", "name"})
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "名称不能为空!")
    @DbField(onlyOn = Contain.class)
    private String name;
    @Column(comment = "排序")
    @OrderBy(asc = true)
    @NotNull(message = "排序不能为空!")
    private Integer orderNo;
    @TableField(exist = false)
    @DbIgnore
    private List<SysDept> children;
    @ExcelProperty(value = "状态", converter = BooleanStringConverter.class)
    @Column(comment = "是否禁用", notNull = true, defaultValue = "0")
    @Trans(type = TransType.DICTIONARY, key = "disabled", ref = "disabledName")
    private Boolean disabled;
    @ExcelIgnore
    @TableField(exist = false)
    @DbIgnore
    private String disabledName;
}
