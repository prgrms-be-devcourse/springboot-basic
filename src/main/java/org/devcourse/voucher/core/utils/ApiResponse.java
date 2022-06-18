package org.devcourse.voucher.core.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private final int statusCode;

    private final T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private final LocalDateTime serverDateTime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), data);
    }

    public static <T> ApiResponse<T> fail(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), data);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getServerDateTime() {
        return serverDateTime;
    }
}
