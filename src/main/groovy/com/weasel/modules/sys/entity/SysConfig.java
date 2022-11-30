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
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import com.weasel.common.base.BaseEntity;
import com.weasel.common.base.excel.convertor.BooleanStringConverter;
import com.weasel.common.base.excel.convertor.EnumStringConverter;
import com.weasel.common.consts.Consts;
import com.weasel.modules.sys.enums.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("配置")
@Data
@Table(value = "sys_config", comment = "配置表", excludeFields = {"serialVersionUID", "entityClass"})
public class SysConfig extends BaseEntity<SysConfig> {
    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    @Column(comment = "名称", notNull = true)
    @Unique(columns = {"tenant_id", "deleted", "name"})
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "名称不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    @DbField(onlyOn = Contain.class)
    private String name;
    @ApiModelProperty(value = "编码")
    @ExcelProperty(value = "编码")
    @Column(comment = "编码", notNull = true, length = 500)
    @Unique(columns = {"tenant_id", "deleted", "code"})
    private String code;
    @ExcelProperty(value = "数据类型", converter = EnumStringConverter.class)
//    @ExcelProperty(value = "数据类型", converter = DataTypeStringConverter.class)
    @Column(comment = "数据类型", notNull = true)
    private DataType dataType;
    @ExcelProperty(value = "值")
    @Column(comment = "值", type = MySqlTypeConstant.TEXT)
    private String val;
    @ExcelProperty(value = "排序")
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
}
