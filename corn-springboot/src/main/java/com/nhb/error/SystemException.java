package com.nhb.error;


import com.nhb.utils.AppHttpCodeEnum;

/**
 * 统一异常处理
 *
 * @author 大只
 * @date 2022/9/21 15:13
 */
public class SystemException extends RuntimeException {
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
