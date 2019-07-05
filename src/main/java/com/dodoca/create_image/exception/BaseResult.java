package com.dodoca.create_image.exception;

import java.io.Serializable;

/**
 * @Author: TianGuangHui
 * @Date: 2018/12/17 15:44
 * @Description:
 */
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 9198402858256143865L;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回结果
     */
    private T data;

    public BaseResult() {
    }

    public BaseResult(T data) {
        this.msg = "success";
        this.code = 100;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

}
