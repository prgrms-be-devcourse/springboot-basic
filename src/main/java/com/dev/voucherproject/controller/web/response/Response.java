package com.dev.voucherproject.controller.web.response;

public record Response<T>(T result, String message) {
    public static <T> Response<T> success(T result) {
        return new Response<>(result, "success");
    }

    public static Response<String> error(String errorMessage) {
        return new Response<>(errorMessage, "fail");
    }
}
