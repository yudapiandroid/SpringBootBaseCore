package com.suse.yuxin.base;

import java.io.Serializable;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description: 提供统一返回消息格式
 */
public class ResponseBean<T> implements Serializable {


    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_SERVER_ERROR = 500;
    public static final int RESPONSE_CODE_CLIENT_ERROR = 400;
    public static final int RESPONSE_CODE_NOT_FOUND = 404;

    private int code = RESPONSE_CODE_OK;
    private String msg = "";

    private T data;

    public ResponseBean(T data) {
        this.data = data;
    }

    public ResponseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
