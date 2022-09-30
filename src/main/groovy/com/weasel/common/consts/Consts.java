package com.weasel.common.consts;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/14 14:07
 */
public interface Consts {
    interface Profile {
        String DEV = "dev";
        String TEST = "test";
        String PROD = "prod";
        String ACTABLE = "actable";
        String NOT_ACTABLE = "!actable";
    }

    interface Session {
        String LOGIN_USER = "LOGIN_USER";
    }

    interface ValidateGroup {

        interface SAVE {
        }

        interface UPDATE {
        }

        interface DELETE {
        }
    }

    interface DefaultSetting {

        Long DEFALUT_CREATOR = 1L;
    }
}
