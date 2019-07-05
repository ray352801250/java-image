package com.dodoca.create_image.exception;

/**
 * 统一返回结果状态信息码
 * @description:
 * @author: tianguanghui
 * @create: 2019-06-28 14:51
 **/
public enum ResultEnum {

    UNKNOWN_ERROR(9999, "未知错误"),
    NEED_LOGIN(-1, "未登录"),
    REPEAT_REGISTER(-2, "该用户已注册"),
    USER_NOT_EXIST(-3, "不存在该用户"),
    PASSWORD_ERROR(-4, "密码错误"),
    EMPTY_USERNAME(-5, "用户名为空"),
    EMPTY_PASSWORD(-6, "密码为空"),
    SUCCESS(100, "success"),
    PARAMETER_MISSING(1001,"必填参数为空"),

    REQUEST_METHOD_ERROR(1002,"接口请求方式不正确"),

    PARAMETRIC_TESTS_ERROR(1010,"参数校检异常"),

    SERVER_EXCEPTION(1011,"服务异常");

    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
