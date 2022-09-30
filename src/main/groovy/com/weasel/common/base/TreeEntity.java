package com.weasel.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ejlchina.searcher.bean.DbIgnore;
import com.tangzc.mpe.actable.annotation.Column;
import com.weasel.modules.sys.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @author weasel
 * @date 2022/7/26 9:21
 * @version 1.0
 */
@Data
public class TreeEntity<T extends BaseEntity<T>> extends BaseEntity<T> {
    @Column(comment = "父节点id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long parentId;
    @TableField(exist = false)
    @DbIgnore
    private List<T> children;
}
