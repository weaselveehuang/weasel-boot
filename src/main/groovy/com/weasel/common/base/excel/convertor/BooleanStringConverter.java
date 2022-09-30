package com.weasel.common.base.excel.convertor;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.fhs.trans.service.impl.DictionaryTransService;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/5/12 9:29
 */
public class BooleanStringConverter extends com.alibaba.excel.converters.booleanconverter.BooleanStringConverter {

    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        DictionaryTransService dictionaryTransService = SpringUtil.getBean(DictionaryTransService.class);
        String mapKey = dictionaryTransService.getMapKey(contentProperty.getField().getName(), cellData.getStringValue());
        return BooleanUtil.toBoolean(dictionaryTransService.getUnTransMap().get(mapKey));
    }

    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        DictionaryTransService dictionaryTransService = SpringUtil.getBean(DictionaryTransService.class);
        String mapKey = dictionaryTransService.getMapKey(contentProperty.getField().getName(), value.toString());
        return new WriteCellData(dictionaryTransService.getDictionaryTransMap().get(mapKey));
    }
}
