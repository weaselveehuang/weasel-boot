package com.weasel.modules.dev.entity;

import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.operator.Contain;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.weasel.common.base.BaseEntity;
import com.weasel.common.consts.Consts;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Table(value = "dev_field", comment = "在线开发表单字段定义表", excludeFields = {"serialVersionUID", "entityClass"})
public class DevField extends BaseEntity<DevField> {

    @Column(comment = "表名", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "表名不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private String tableName;
    @Column(comment = "列名", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "列名不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    @DbField(onlyOn = Contain.class)
    private String colName;
    @Column(comment = "字段描述")
    @TableField(condition = SqlCondition.LIKE)
    @DbField(onlyOn = Contain.class)
    private String comment;
    @Column(comment = "标题", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @DbField(onlyOn = Contain.class)
    private String title;
    @OrderBy(asc = true)
    @NotNull(message = "排序不能为空!")
    private Integer orderNo;
    /**
     * 以下为列表属性
     */
    @Column(comment = "列表单元格展示组件")
    private String viewComponent;
    @Column(comment = "是否转码")
    private Boolean shouldConvert;
    @Column(comment = "宽度")
    private String width;
    @Column(comment = "是否查询条件")
    private Boolean search;
    @Column(comment = "列表查询表单项组件")
    private String searchComponent;
    /**
     * 以下为表单属性
     */
    @Column(comment = "编辑组件")
    private String editComponent;
}
