package com.waterfogsw.voucher.voucher.dto;

public record Response<T>(
        T body,
        ResponseStatus status
) {
    public static <T> Response<T> ok(T body) {
        return new Response<>(body, ResponseStatus.OK);
    }

    public static <T> Response<T> error(ResponseStatus status) {
        return new Response<>(null, status);
    }
}
