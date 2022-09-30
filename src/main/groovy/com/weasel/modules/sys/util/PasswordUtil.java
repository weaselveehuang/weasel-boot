package com.weasel.modules.sys.util;

import cn.hutool.crypto.SecureUtil;
import lombok.experimental.UtilityClass;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/30 16:29
 */
@UtilityClass
public class PasswordUtil {
    public static String encrypt(String salt, String password) {

        String x = SecureUtil.sha256()
                .setSalt(salt.getBytes())
                .digestHex(password);
        return x;
    }

}
