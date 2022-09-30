package com.weasel.common.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.DbIgnore;
import com.ejlchina.searcher.operator.Between;
import com.ejlchina.searcher.operator.Contain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.annotation.InsertOptionDate;
import com.tangzc.mpe.annotation.InsertOptionUser;
import com.tangzc.mpe.annotation.UpdateOptionDate;
import com.tangzc.mpe.annotation.UpdateOptionUser;
import com.weasel.common.consts.Consts;
import com.weasel.config.MybatisPlusConfig;
import com.weasel.modules.sys.entity.SysUser;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/24 17:04
 */
@Data
public class BaseEntity<T extends BaseEntity<T>> extends Model<T> implements TransPojo {

    @Column(comment = "租户id", notNull = true)
    @JSONField(serialize = false)
    private Long tenantId;
    @Column(isKey = true, comment = "主键id")
    @NotNull(groups = {Consts.ValidateGroup.UPDATE.class, Consts.ValidateGroup.DELETE.class})
    private Long id;
    @ExcelProperty("备注")
    @Column(comment = "备注", length = 500)
    @DbField(onlyOn = Contain.class)
    private String description;
    @Column(comment = "乐观锁版本号", defaultValue = "0", notNull = true)
    @Version
    private Long version;
    @Column(comment = "是否删除", defaultValue = "0")
    @JSONField(serialize = false)
    private Boolean deleted;
    @Column(comment = "创建人", notNull = true)
    @TableField(fill = FieldFill.INSERT)
    @InsertOptionUser(MybatisPlusConfig.UserIdAutoFillHandler.class)
    @Trans(type = TransType.SIMPLE, target = SysUser.class, ref = "createByName", fields = "username")
    private Long createBy;
    @ExcelProperty("创建人")
    @TableField(exist = false)
    @DbIgnore
    private String createByName;
    @ExcelProperty("创建时间")
    @Column(comment = "创建时间", notNull = true)
    @TableField(fill = FieldFill.INSERT)
    @OrderBy(sort = Short.MAX_VALUE)
    @InsertOptionDate
    @DbField(onlyOn = Between.class)
    private LocalDateTime createTime;
    @Column(comment = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    @UpdateOptionUser(MybatisPlusConfig.UserIdAutoFillHandler.class)
    private Long updateBy;
    @Column(comment = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    @UpdateOptionDate
    @DbField(onlyOn = Between.class)
    private LocalDateTime updateTime;
    @TableField(exist = false)
    @DbIgnore
    @ExcelIgnore
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    private Map<String, String> transMap;
}
