package kdt.vouchermanagement.global.response;

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
}
