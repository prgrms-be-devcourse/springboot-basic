package com.zerozae.voucher.common.response;

import lombok.Getter;


@Getter
public class Response<T> {

    private boolean isSuccess;
    private String message;
    private T data;

    public Response(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public Response(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public static Response success() {
        return new Response(true, "완료 되었습니다.");
    }

    public static <T> Response success(T data) {
        return new Response(true,  data);
    }
}

