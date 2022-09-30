package com.weasel.modules.sys.excel.convertor;

import cn.hutool.core.util.EnumUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.weasel.modules.sys.enums.DataScope;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/5/12 9:29
 */
public class DataScopeStringConverter implements Converter<DataScope> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return DataScope.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public DataScope convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return EnumUtil.getBy(DataScope.class, dataScope -> dataScope.getDescription().equals(cellData.getStringValue()));
    }

    @Override
    public WriteCellData<?> convertToExcelData(DataScope value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(value.getDescription());
    }
}
