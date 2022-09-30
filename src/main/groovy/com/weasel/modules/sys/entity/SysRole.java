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
import com.weasel.common.base.BaseEntity;
import com.weasel.common.base.TreeEntity;
import com.weasel.common.base.excel.convertor.BooleanStringConverter;
import com.weasel.common.consts.Consts;
import com.weasel.modules.sys.enums.DataScope;
import com.weasel.modules.sys.excel.convertor.DataScopeStringConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Table(value = "sys_role", comment = "角色表", excludeFields = {"serialVersionUID", "entityClass"})
public class SysRole extends TreeEntity<SysRole> {
    @ExcelProperty("名称")
    @Column(comment = "名称", notNull = true)
    @Unique(columns = {"tenant_id", "deleted", "name"})
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "名称不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    @DbField(onlyOn = Contain.class)
    private String name;
    @ExcelProperty("编码")
    @Column(comment = "编码", notNull = true, length = 500)
    @Unique(columns = {"tenant_id", "deleted", "code"})
    @NotBlank(message = "编码不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private String code;
    @ExcelProperty("互斥角色")
    @Column(comment = "互斥角色")
    private String mutexIds;
    @ExcelProperty("是否运行时互斥")
    @Column(comment = "是否运行时互斥")
    private Boolean runtimeMutex;
    @ExcelProperty("先决条件角色")
    @Column(comment = "先决条件角色")
    private String prerequisiteIds;
    @ExcelProperty("用户数量")
    @Column(comment = "用户数量")
    private Integer userCount;
    @ExcelProperty("排序")
    @Column(comment = "排序")
    @OrderBy(asc = true, sort = Short.MIN_VALUE)
    private Integer orderNo;
    @ExcelProperty(value = "状态", converter = BooleanStringConverter.class)
    @Column(comment = "是否禁用", notNull = true, defaultValue = "0")
    @Trans(type = TransType.DICTIONARY, key = "disabled", ref = "disabledName")
    private Boolean disabled;
    @ExcelIgnore
    @TableField(exist = false)
    @DbIgnore
    private String disabledName;
    //    @ExcelIgnore
    @ExcelProperty(value = "数据权限", converter = DataScopeStringConverter.class)
    @Column(comment = "数据权限", notNull = true)
    @Trans(type = TransType.ENUM, key = "description", ref = "dataScopeName")
    @NotNull(message = "数据权限不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private DataScope dataScope;
    @ExcelIgnore
//    @ExcelProperty("数据权限")
    @TableField(exist = false)
    @DbIgnore
    private String dataScopeName;

    @TableField(exist = false)
    @DbIgnore
    private List<Long> menu;
}
