package com.example.springbootbasic.controller.request;

import static com.example.springbootbasic.controller.request.RequestCode.FAIL;
import static com.example.springbootbasic.controller.request.RequestCode.SUCCESS;

public class RequestBody<T> {
    private Integer code;
    private T data;

    private RequestBody(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> RequestBody<T> success(T data) {
        return new RequestBody<>(SUCCESS.code(), data);
    }

    public static <T> RequestBody<T> fail(T data) {
        return new RequestBody<>(FAIL.code(), data);
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}