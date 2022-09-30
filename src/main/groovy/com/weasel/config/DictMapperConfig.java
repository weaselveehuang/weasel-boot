package com.weasel.config;

import ai.yue.library.base.view.R;
import com.power4j.kit.common.data.dict.support.RestResponseProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/7 10:42
 */
@Configuration
public class DictMapperConfig {

    @Bean
    public RestResponseProcessor<?> restResponseProcessor() {
        return data -> R.success(data);
    }
}
