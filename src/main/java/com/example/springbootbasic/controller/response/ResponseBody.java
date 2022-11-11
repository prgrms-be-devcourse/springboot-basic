package com.example.springbootbasic.controller.response;

import static com.example.springbootbasic.controller.response.ResponseCode.FAIL;
import static com.example.springbootbasic.controller.response.ResponseCode.SUCCESS;

public class ResponseBody<T> {
    private Integer code;
    private T data;

    private ResponseBody(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseBody<T> success(T data) {
        return new ResponseBody<>(SUCCESS.code(), data);
    }

    public static <T> ResponseBody<T> fail(T data) {
        return new ResponseBody<>(FAIL.code(), data);
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}