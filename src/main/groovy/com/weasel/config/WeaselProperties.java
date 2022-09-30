package com.weasel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/11 14:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "weasel")
public class WeaselProperties {
    /**
     * 权限认证排除路径
     */
    String[] excludePath;
}
