package com.weasel.modules.dev.entity;

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
@Table(value = "dev_table", comment = "在线开发表单通用属性表", excludeFields = {"serialVersionUID", "entityClass"})
public class DevTable extends BaseEntity<DevTable> {

    @Column(comment = "数据源名称", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "数据源名称不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private String dsName;

    @Column(comment = "表名", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "表名不能为空!", groups = Consts.ValidateGroup.SAVE.class)
    private String tableName;
    @Column(comment = "表描述")
    @TableField(condition = SqlCondition.LIKE)
    @DbField(onlyOn = Contain.class)
    private String comment;
    @Column(comment = "业务名称", notNull = true)
    @TableField(condition = SqlCondition.LIKE)
    @DbField(onlyOn = Contain.class)
    private String biz;

    @Column(comment = "点击行是否选中")
    private Boolean clickToRowSelect;
    @Column(comment = "是否树表")
    private Boolean isTreeTable;
    @Column(comment = "自定义排序方法")
    private String sortFn;
    @Column(comment = "自定义过滤方法")
    private String filterFn;
    @Column(comment = "是否取消表格的默认 padding")
    private Boolean inset;
    @Column(comment = "是否显示表格设置工具")
    private Boolean showTableSetting;
    @Column(comment = "表格设置工具配置")
    private String tableSetting;
    @Column(comment = "是否显示斑马纹")
    private Boolean striped;
    @Column(comment = "是否自动生成 key")
    private Boolean autoCreateKey;
    @Column(comment = "计算合计行的方法")
    private String summaryFunc;
    @Column(comment = "自定义合计数据")
    private String summaryData;
    @Column(comment = "是否显示合计行")
    private Boolean showSummary;
    @Column(comment = "是否可拖拽列")
    private Boolean canColDrag;
    @Column(comment = "接口请求对象；crud组件通过路由参数解析生成此对象；保留此字段，以便后续自定义组件用到")
    private String api;
    @Column(comment = "请求之前对参数进行处理")
    private String beforeFetch;
    @Column(comment = "请求之后对返回值进行处理")
    private String afterFetch;
    @Column(comment = "开启表单后，在请求之前处理搜索条件参数")
    private String handleSearchInfoFn;
    @Column(comment = "接口请求配置，可以配置请求的字段和响应的字段名")
    private String fetchSetting;
    @Column(comment = "组件加载后是否立即请求接口，在 api 有传的情况下，如果为 false，需要自行使用 reload 加载表格数据")
    private Boolean immediate;
    @Column(comment = "在启用搜索表单的前提下，是否在表格没有数据的时候显示表格")
    private Boolean emptyDataIsShowTable;
    @Column(comment = "额外的请求参数")
    private String searchInfo;
    @Column(comment = "默认的排序参数")
    private String defSort;
    @Column(comment = "使用搜索表单")
    private Boolean useSearchForm;
    @Column(comment = "表单配置；crud组件通过路由参数解析生成此对象；保留此字段，以便后续自定义组件用到")
    private String formConfig;
    @Column(comment = "表单列信息；crud组件通过路由参数解析生成此对象；保留此字段，以便后续自定义组件用到")
    private String columns;
    @Column(comment = "是否显示序号列")
    private Boolean showIndexColumn;
    @Column(comment = "序号列配置")
    private String indexColumnProps;
    @Column(comment = "序号列配置；crud组件通过路由参数解析生成此对象；保留此字段，以便后续自定义组件用到")
    private String actionColumn;
    @Column(comment = "文本超过宽度是否显示...")
    private Boolean ellipsis;
    @Column(comment = "是否继承父级高度（父级高度-表单高度-padding高度）")
    private Boolean isCanResizeParent;
    @Column(comment = "是否可以自适应高度(如果置于PageWrapper组件内，请勿启用PageWrapper的fixedHeight属性，二者不可同时使用)")
    private Boolean canResize;
    @Column(comment = "表格自适应高度计算结果会减去这个值")
    private Double resizeHeightOffset;
    @Column(comment = "切换页码是否重置勾选状态")
    private Boolean clearSelectOnPageChange;
    @Column(comment = "rowKey")
    private String rowKey;
    @Column(comment = "表格数据，非 api 加载情况；crud组件通过路由参数解析生成此对象；保留此字段，以便后续自定义组件用到")
    private String dataSource;
    @Column(comment = "表格标题右侧温馨提醒")
    private String titleHelpMessage;
    @Column(comment = "表格最大高度，超出会显示滚动条")
    private Double maxHeight;
    @Column(comment = "是否显示表格边框")
    private Boolean bordered;
    @Column(comment = "分页信息配置，为 false 不显示分页")
    private String pagination;
    @Column(comment = "表格 loading 状态")
    private Boolean loading;
    @Column(comment = "/**\n" +
            "   * The column contains children to display\n" +
            "   * @default 'children'\n" +
            "   * @type string | string[]\n" +
            "   */")
    private String childrenColumnName;

    @Column(comment = "/**\n" +
            "     * Override default table elements\n" +
            "     * @type object\n" +
            "     */")
    private String components;

    @Column(comment = "/**\n" +
            "   * Expand all rows initially\n" +
            "   * @default false\n" +
            "   * @type boolean\n" +
            "   */")
    private Boolean defaultExpandAllRows;

    @Column(comment = "/**\n" +
            "   * Initial expanded row keys\n" +
            "   * @type string[]\n" +
            "   */")
    private String defaultExpandedRowKeys;

    @Column(comment = "/**\n" +
            "     * Current expanded row keys\n" +
            "     * @type string[]\n" +
            "     */")
    private String expandedRowKeys;

    @Column(comment = "/**\n" +
            "     * Expanded container render for each row\n" +
            "     * @type Function\n" +
            "     */")
    private String expandedRowRender;

    @Column(comment = "/**\n" +
            "     * Customize row expand Icon.\n" +
            "     * @type Function | VNodeChild\n" +
            "     */")
    private String expandIcon;

    @Column(comment = "/**\n" +
            "     * Whether to expand row by clicking anywhere in the whole row\n" +
            "     * @default false\n" +
            "     * @type boolean\n" +
            "     */")
    private Boolean expandRowByClick;

    @Column(comment = "/**\n" +
            "     * The index of `expandIcon` which column will be inserted when `expandIconAsCell` is false. default 0\n" +
            "     */")
    private Integer expandIconColumnIndex;

    @Column(comment = "/**\n" +
            "     * Table footer renderer\n" +
            "     * @type Function | VNodeChild\n" +
            "     */")
    private String footer;

    @Column(comment = "/**\n" +
            "     * Indent size in pixels of tree data\n" +
            "     * @default 15\n" +
            "     * @type number\n" +
            "     */")
    private Integer indentSize;

    @Column(comment = "/**\n" +
            "     * i18n text including filter, sort, empty text, etc\n" +
            "     * @default { filterConfirm: 'Ok', filterReset: 'Reset', emptyText: 'No Data' }\n" +
            "     * @type object\n" +
            "     */")
    private String locale;

    @Column(comment = "/**\n" +
            "     * Row's className\n" +
            "     * @type Function\n" +
            "     */")
    private String rowClassName;

    @Column(comment = "/**\n" +
            "     * Row selection config\n" +
            "     * @type object\n" +
            "     */")
    private String rowSelection;

    @Column(comment = "/**\n" +
            "     * Set horizontal or vertical scrolling, can also be used to specify the width and height of the scroll area.\n" +
            "     * It is recommended to set a number for x, if you want to set it to true,\n" +
            "     * you need to add style .ant-table td { white-space: nowrap; }.\n" +
            "     * @type object\n" +
            "     */")
    private String scroll;

    @Column(comment = "/**\n" +
            "     * Whether to show table header\n" +
            "     * @default true\n" +
            "     * @type boolean\n" +
            "     */")
    private Boolean showHeader;

    @Column(comment = "/**\n" +
            "     * Size of table\n" +
            "     * @default 'default'\n" +
            "     * @type string\n" +
            "     */")
    private String size;

    @Column(comment = "表格标题，非 api 加载情况；crud组件通过路由参数解析生成此对象；保留此字段，以便后续自定义组件用到")
    private String title;

    @Column(comment = "/**\n" +
            "     * Set props on per header row\n" +
            "     * @type Function\n" +
            "     */")
    private String customHeaderRow;

    @Column(comment = "/**\n" +
            "     * Set props on per row\n" +
            "     * @type Function\n" +
            "     */")
    private String customRow;

    @Column(comment = "/**\n" +
            "     * `table-layout` attribute of table element\n" +
            "     * `fixed` when header/columns are fixed, or using `column.ellipsis`\n" +
            "     *\n" +
            "     * @see https://developer.mozilla.org/en-US/docs/Web/CSS/table-layout\n" +
            "     * @version 1.5.0\n" +
            "     */")
    private String tableLayout;

    @Column(comment = "/**\n" +
            "     * the render container of dropdowns in table\n" +
            "     * @param triggerNode\n" +
            "     * @version 1.5.0\n" +
            "     */")
    private String getPopupContainer;

    @Column(comment = "/**\n" +
            "     * Data can be changed again before rendering.\n" +
            "     * The default configuration of general user empty data.\n" +
            "     * You can configured globally through [ConfigProvider](https://antdv.com/components/config-provider-cn/)\n" +
            "     *\n" +
            "     * @version 1.5.4\n" +
            "     */")
    private String transformCellText;

    @Column(comment = "单元格编辑状态提交回调，返回false将阻止单元格提交数据到table。该回调在行编辑模式下无效。")
    private String beforeEditSubmit;

    @Column(comment = "/**\n" +
            "     * Callback executed when pagination, filters or sorter is changed\n" +
            "     * @param pagination\n" +
            "     * @param filters\n" +
            "     * @param sorter\n" +
            "     * @param currentDataSource\n" +
            "     */")
    private String onChange;

    @Column(comment = "/**\n" +
            "     * Callback executed when the row expand icon is clicked\n" +
            "     *\n" +
            "     * @param expanded\n" +
            "     * @param record\n" +
            "     */")
    private String onExpand;

    @Column(comment = "/**\n" +
            "     * Callback executed when the expanded rows change\n" +
            "     * @param expandedRows\n" +
            "     */")
    private String onExpandedRowsChange;


    @Column
    private String onColumnsChange;
}
