package com.weasel.modules.sys.excel.convertor;

import cn.hutool.core.util.EnumUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.weasel.modules.sys.enums.MenuType;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/5/12 9:39
 */
public class MenuTypeStringConverter implements Converter<MenuType> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return MenuType.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public MenuType convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return EnumUtil.getBy(MenuType.class, dataScope -> dataScope.getValue().equals(cellData.getStringValue()));
    }

    @Override
    public WriteCellData<?> convertToExcelData(MenuType value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(value.getValue());
    }
}
