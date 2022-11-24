package com.nhb.utils;

import com.nhb.entity.Record;

/**
 * 响应状态枚举类
 *
 * @author 大只
 * @date 2022-11-06
 */
public enum AppHttpCodeEnum {
    SUCCESS(200, "操作成功"),
    ERROR(400, "操作失败"),

    NEED_LOGIN(401, "需要登录后操作"),
    NO_PNG(400,"不支持识别PNG格式的图片"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    AI_ERROR(500, "AI服务器异常"),
    USERNAME_EXIST(501, "用户名已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    USERNAME_PASSWORD_NOT_NULL(405, "账号或密码为空"),
    USER_ERROR(400,"正在使用当前用户，不得操作"),
    USER_BAN(400,"账号停用"),
    LOGIN_ERROR(401, "账号或密码错误"),
    LOCATION_REPETITION(400,"同一个位置只能标记一次");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}


