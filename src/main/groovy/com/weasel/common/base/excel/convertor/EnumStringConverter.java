package com.weasel.common.base.excel.convertor;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/5/12 9:29
 */
public class EnumStringConverter implements Converter<IEnum<String>> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return IEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(IEnum<String> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(value.getValue());
    }
}
