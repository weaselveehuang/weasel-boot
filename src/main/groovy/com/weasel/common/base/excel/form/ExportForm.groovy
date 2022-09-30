package com.weasel.common.base.excel.form

import com.alibaba.excel.support.ExcelTypeEnum

class ExportForm {
    String[] onlySelect
    ScopeType scopeType
    String filename
    ExcelTypeEnum bookType

    enum ScopeType {
        all("全部"),
        filtered("已筛选"),
        selected("已勾选"),
        page("当前页");

        final String value

        ScopeType(String value) {
            this.value = value;
        }
    }
}
