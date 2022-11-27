package com.example.springbootbasic.controller.request;

import static com.example.springbootbasic.controller.request.RequestCode.FAIL;
import static com.example.springbootbasic.controller.request.RequestCode.SUCCESS;

public class RequestBody<T> {
    private final RequestCode code;
    private final T data;

    private RequestBody(RequestCode code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> RequestBody<T> success(T data) {
        return new RequestBody<>(SUCCESS, data);
    }

    public static <T> RequestBody<T> fail(T data) {
        return new RequestBody<>(FAIL, data);
    }

    public RequestCode getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}