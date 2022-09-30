package com.weasel.config;

import ai.yue.library.base.util.ExceptionUtils;
import ai.yue.library.base.view.R;
import ai.yue.library.base.view.Result;
import ai.yue.library.web.util.ServletUtils;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.exception.ErrorCreateDataSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/4/1 10:11
 */
@Slf4j
@RestControllerAdvice
public class WeaselExceptionHandler {

    /**
     * 验证异常统一处理-433
     * @param e 验证异常
     * @return 结果
     */
    @ResponseBody
    @ExceptionHandler(ValidateException.class)
    public Result<?> validateExceptionHandler(ValidateException e) {
        ServletUtils.getResponse().setStatus(433);
        ExceptionUtils.printException(e);
        try {
            JSONArray array = JSONUtil.parseArray(e.getMessage());
            String msg = JSONUtil.parseObj(array.get(0)).getStr("errorHintMsg");
            return R.errorPrompt(msg);
        } catch (Exception exception) {
            return R.paramCheckNotPass(e.getMessage());
        }
    }

    /**
     * 拦截登录异常（User）-401
     *
     * @param e 登录异常
     * @return 结果
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public Result<?> notLoginExceptionHandler(NotLoginException e) {
        return R.unauthorized();
    }

    /**
     * 拦截所有未处理异常-500
     *
     * @param e 异常
     * @return 结果
     */
    @ResponseBody
    @ExceptionHandler(ErrorCreateDataSourceException.class)
    public Result<?> exceptionHandler(ErrorCreateDataSourceException e) {
        ExceptionUtils.printException(e);
        return Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg("创建数据源失败，请检查配置信息是否正确！").flag(false).build();
    }

    /**
     * 拦截所有未处理异常-500
     *
     * @param e 异常
     * @return 结果
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        ExceptionUtils.printException(e);
        return Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg(e.getMessage()).flag(false).build();
    }
}
