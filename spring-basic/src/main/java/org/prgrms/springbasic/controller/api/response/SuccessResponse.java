package org.prgrms.springbasic.controller.api.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record SuccessResponse<T>(LocalDateTime timestamp, String path, String query, int statusCode, String responseMessage, T data) {

    @Builder
    public SuccessResponse {
    }

    public static <T> SuccessResponse<T> responseOf(LocalDateTime timestamp, String path, int statusCode, String responseMessage, T data) {
        return SuccessResponse.<T>builder()
                .timestamp(timestamp)
                .path(path)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }

    public static <T> SuccessResponse<T> responseOf(LocalDateTime timestamp, String path, String query, int statusCode, String responseMessage, T data) {
        return SuccessResponse.<T>builder()
                .timestamp(timestamp)
                .path(path)
                .query(query)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }
}