package kdt.vouchermanagement.global.response;

import java.text.MessageFormat;

public class Response<T> {
    private final int statusCode;
    private final T data;

    public Response(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> Response<T> of(int statusCode, T data) {
        return new Response<>(statusCode, data);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1}", statusCode, data);
    }
}
