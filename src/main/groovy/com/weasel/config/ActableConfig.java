package com.weasel.config;

import com.tangzc.mpe.actable.EnableAutoTable;
import com.weasel.common.consts.Consts;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/31 14:05
 */
@Configuration
@EnableAutoTable(activeProfile = {Consts.Profile.DEV, Consts.Profile.ACTABLE})
@Profile({Consts.Profile.ACTABLE})
public class ActableConfig {

}
