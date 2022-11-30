package com.weasel.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.DbIgnore;
import com.ejlchina.searcher.operator.Contain;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Table;
import com.weasel.common.base.BaseEntity;
import com.weasel.common.base.TreeEntity;
import com.weasel.modules.sys.enums.MenuType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("菜单")
@Data
@Table(value = "sys_menu", comment = "菜单表", excludeFields = {"serialVersionUID", "entityClass"})
public class SysMenu extends TreeEntity<SysMenu> {
    @ApiModelProperty(value = "权限标识")
    @Column(comment = "权限标识", length = 500)
    private String permission;
    @Column(comment = "类型: CATALOG 目录,MENU 菜单,BUTTON 按钮", notNull = true)
    @NotNull(message = "类型不能为空!")
    private MenuType type;
    @Column(comment = "是否禁用", notNull = true, defaultValue = "0")
    private Boolean disabled;
    @Column(comment = "路由地址", length = 500)
    private String path;
    @Column(comment = "组件路径", length = 500)
    private String component;
    @Column(comment = "排序")
    @OrderBy(asc = true)
    @NotNull(message = "排序不能为空!")
    private Integer orderNo;
    @Column(comment = "标题", length = 500, notNull = true)
//    @Unique(columns = {"tenant_id", "deleted", "parent_id", "title"})
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "标题不能为空!")
    @DbField(onlyOn = Contain.class)
    private String title;
    @Column(comment = "获取动态路由打开数，超过 0 即代表需要控制打开数")
    private Integer dynamicLevel;
    @Column(comment = "是否忽略缓存", defaultValue = "1")
    private Boolean ignoreKeepAlive;
    @Column(comment = "图标")
    private String icon;
    @Column(comment = "外链地址", length = 500)
    private String frameSrc;
    @Column(comment = "是否隐藏面包屑", defaultValue = "0")
    private Boolean hideBreadcrumb;
    @Column(comment = "是否隐藏", defaultValue = "0")
    private Boolean hideMenu;
    @Column(comment = "是否隐藏标签页", defaultValue = "0")
    private Boolean hideTab;
    @Column(comment = "是否外链", defaultValue = "0")
    private Boolean isLink;
    @Column(comment = "是否忽略路由", defaultValue = "0")
    private Boolean ignoreRoute;
    @Column(comment = "跳转路由")
    private String redirect;

    /**
     * 以下btn开头的属性为按钮属性
     */
    @Column(comment = "按钮位置：INLINE | TOOLBAR | BATCH")
    @DbIgnore
    private String btnPosition;
    @Column(comment = "按钮类型：新增|编辑|删除|详情 等，对应===POST /{id}===DELETE /{id}===GET /{id}===PUT 等")
    @DbIgnore
    private String btnAction;

    @Column(comment = "按钮单击事件")
    @DbIgnore
    private String btnOnClick;
    @Column(comment = "按钮标签")
    @DbIgnore
    private String btnLabel;
    @Column(comment = "按钮颜色：'success' | 'error' | 'warning'")
    @DbIgnore
    private String btnColor;
    @Column(comment = "按钮弹出确认框事件")
    @DbIgnore
    private String btnPopConfirm;
    @Column(comment = "按钮后是否显示分隔符")
    @DbIgnore
    private String btnDivider;
    @Column(comment = "按钮是否显示")
    @DbIgnore
    private String btnIfShow;
    @Column(comment = "按钮提示字符")
    @DbIgnore
    private String btnTooltip;
}
