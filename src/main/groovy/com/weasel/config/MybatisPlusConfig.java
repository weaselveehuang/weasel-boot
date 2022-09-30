package com.weasel.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.tangzc.mpe.annotation.handler.IOptionByAutoFillHandler;
import com.weasel.common.consts.Consts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/31 14:05
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Component
    public class UserIdAutoFillHandler implements IOptionByAutoFillHandler<Long> {

        /**
         * @param object 当前操作的数据对象
         * @param clazz  当前操作的数据对象的class
         * @param field  当前操作的数据对象上的字段
         * @retur
         */
        @Override
        public Long getVal(Object object, Class<?> clazz, Field field) {
            Object fieldValue = ReflectUtil.getFieldValue(object, field);
            if (ObjectUtil.isNotNull(fieldValue)) {
                return Convert.toLong(fieldValue);
            }
            try {
                return StpUtil.getLoginIdAsLong();
            } catch (Exception e) {
                return Consts.DefaultSetting.DEFALUT_CREATOR;
            }
        }
    }
}
