package com.weasel.config;

import cn.hutool.core.map.MapUtil;
import com.fhs.trans.service.impl.DictionaryTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/5/10 15:14
 */
@Configuration
public class EasyTransConfig {
    public static final String CACHE_KEY_DISABLED = "disabled";
    public static final String CACHE_KEY_SEX = "sex";
    @Autowired  //注入字典翻译服务
    private DictionaryTransService dictionaryTransService;

    //在某处将字典缓存刷新到翻译服务中，以下是demo
    @PostConstruct
    public void transMap() {
        dictionaryTransService.refreshCache(CACHE_KEY_DISABLED, MapUtil.<String, String>builder().put("true", "已禁用").put("false", "已启用").build());
        dictionaryTransService.refreshCache(CACHE_KEY_SEX, MapUtil.<String, String>builder().put("1", "男").put("2", "女").build());
    }
}
