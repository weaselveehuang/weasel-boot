package com.weasel.modules.sys.util

import cn.hutool.core.util.RandomUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.crypto.SecureUtil

class PasswordUtil {
    public static String encrypt(String salt, String password) {

        String x = SecureUtil.sha256()
                .setSalt(salt.getBytes())
                .digestHex(password);
        return x;
    }

    def a() {
        println 1
    }

    def b() {
        println 2
    }

    def c() {
        println 3
    }

//    def static call1(m) {
//        eval(m)
//    }

    static void main(String[] args) {
//        call1('c')
        def c = SecureUtil.&sha256

        def c1 = c()
        println c1.setSalt(RandomUtil.randomBytes(6))
                .digestHex('password')
    }
}
