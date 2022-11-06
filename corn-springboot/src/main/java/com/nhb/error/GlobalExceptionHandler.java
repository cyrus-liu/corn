package com.nhb.error;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常拦截器
 *
 * @author 大只
 * @date 2022/9/21 15:14
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return Result.errorResult(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);

        // 如果是未登录异常
        if (e instanceof NotLoginException) {
            NotLoginException ee = (NotLoginException) e;
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), ee.getMessage());
        } else if (e instanceof NotPermissionException) {
            // 如果是权限异常
            return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), "权限不足");
        }
        
        //从异常对象中获取提示信息封装返回
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result argumentExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        log.info("发生参数异常:{}", message);
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), message);
    }

}
