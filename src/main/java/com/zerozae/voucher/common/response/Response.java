package com.zerozae.voucher.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static <T> Response<T> success(T data) {
        return new Response(true,  data);
    }

    public static Response failure(String message) {
        return new Response(false, message);
    }
}
