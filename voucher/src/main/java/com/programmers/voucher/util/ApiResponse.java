package com.programmers.voucher.util;

public class ApiResponse<T> {
    boolean success;
    T result;
    String error;

    public ApiResponse(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public ApiResponse(boolean success, T result, String error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    // reference from ResponseEntity.of
    public static <U> ApiResponse<U> of(U result) {
        return new ApiResponse<>(true, result, null);
    }

    public static <V> ApiResponse<V> failed(String message) {
        return new ApiResponse<>(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
