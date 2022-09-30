package com.weasel.modules.dev.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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

@Data
@Table(value = "dev_datasource", comment = "在线开发数据源表", excludeFields = {"serialVersionUID", "entityClass"})
public class DevDatasource extends BaseEntity<DevDatasource> {
    @ExcelProperty("名称")
    @Column(comment = "名称", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "名称不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    @DbField(onlyOn = Contain.class)
    private String name;
    @ExcelProperty("驱动类")
    @Column(comment = "驱动类", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "驱动类不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    @DbField(onlyOn = Contain.class)
    private String driverClassName;
    @ExcelProperty("url")
    @Column(comment = "url", notNull = true)
    @NotBlank(message = "url不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private String url;
    @Column(comment = "用户名", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "用户名不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    @DbField(onlyOn = Contain.class)
    private String username;
    @Column(comment = "密码", notNull = true)
    @NotBlank(message = "密码不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private String password;
}
