package com.dev.voucherproject.controller.web.response;

public class Response <T>{
    private final T result;
    private final String message;

    public Response(T result, String message) {
        this.result = result;
        this.message = message;
    }

    public static <T> Response<T> success(T result) {
        return new Response<>(result, "success");
    }

    public static Response<String> error(String errorMessage) {
        return new Response<>(errorMessage, "fail");
    }

    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
