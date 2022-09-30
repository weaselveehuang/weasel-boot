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
@MapDict(code = "defaultRole", name = "系统默认角色")
public enum DefaultRole {
    @Label("超级管理员")
    SUPER_ADMIN("SUPER_ADMIN", false),

    @Label("管理员")
    ADMIN("ADMIN", true),

    @Label("普通用户")
    USER("USER", true);

    @EnumValue
    @DictValue
    private final String value;
    //    @DictValue
    private final Boolean canGrantPermission;
}
