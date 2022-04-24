package org.prgms.voucheradmin.global.common;

public class ResponseDto<T> {
    private int status;
    private String message;
    private T result;

    public ResponseDto(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
