package com.weasel.modules.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
@MapDict(code = "dataScope", name = "数据权限范围")
public enum DataScope {
    @Label("全部")
    ALL("ALL", "全部"),

    @Label("本人")
    SELF("SELF", "本人"),

    @Label("本人及子级")
    SELF_CHILDREN("SELF_CHILDREN", "本人及子级"),

    @Label("本级")
    LEVEL("LEVEL", "本级"),

    @Label("本级及子级")
    LEVEL_CHILDREN("LEVEL_CHILDREN", "本级及子级"),

    @Label("自定义")
    CUSTOM("CUSTOM", "自定义");

    @EnumValue
    @DictValue
    private final String value;

    private final String description;

//    @Override
//    public String toString() {
//        return this.value;
//    }
}
