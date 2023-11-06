package org.programmers.springorder.voucher.dto;

public class Response<T> {
    private final String resultCode;
    private final T data;

    public Response(String resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }
    public static Response<Void> error(String errorCode){
        return new Response<>(errorCode, null);
    }

    public static Response<Void> success(){
        return new Response<Void>("SUCCESS", null);
    }
    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }
}
