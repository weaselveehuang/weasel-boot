package com.weasel.common.util

/**
 * @author weasel
 * @date 2022/4/18 10:37
 * @version 1.0
 */
class ListUtil {
    static List<Long> getDiff(List<Long> newIds, List<Long> oldIds) {
        newIds - oldIds
    }
}
