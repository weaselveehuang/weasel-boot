package com.weasel.modules.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.power4j.kit.common.data.dict.annotation.DictValue;
import com.power4j.kit.common.data.dict.annotation.Label;
import com.power4j.kit.common.data.dict.annotation.MapDict;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/6 16:57
 */
@Getter
@AllArgsConstructor
@MapDict(code = "dataType", name = "数据类型")
public enum DataType implements IEnum<String> {
    @Label("TEXT")
    TEXT("TEXT"),

    @Label("JSON")
    JSON("JSON");

    @EnumValue
    @DictValue
    private final String value;

//    @Override
//    public String toString() {
//        return this.value;
//    }
}
