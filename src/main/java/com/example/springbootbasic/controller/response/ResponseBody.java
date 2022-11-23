package com.example.springbootbasic.controller.response;

import static com.example.springbootbasic.controller.response.ResponseCode.FAIL;
import static com.example.springbootbasic.controller.response.ResponseCode.SUCCESS;

public class ResponseBody<T> {
    private final ResponseCode code;
    private final T data;

    private ResponseBody(ResponseCode code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseBody<T> success(T data) {
        return new ResponseBody<>(SUCCESS, data);
    }

    public static <T> ResponseBody<T> fail(T data) {
        return new ResponseBody<>(FAIL, data);
    }

    public ResponseCode getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return code.isSuccess();
    }
}