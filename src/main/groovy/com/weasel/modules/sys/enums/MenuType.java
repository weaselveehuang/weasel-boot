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
@MapDict(code = "menuType", name = "菜单类型")
public enum MenuType {
    @Label("目录")
    CATALOG("CATALOG"),

    @Label("菜单")
    MENU("MENU"),

    @Label("按钮")
    BUTTON("BUTTON");

    @EnumValue
    @DictValue
    private final String value;
}
