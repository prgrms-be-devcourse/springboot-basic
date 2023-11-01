package org.prgrms.vouchermanager.common;

public record ApiResponse(
        ApiStatus status,
        String message,
        Object data
) {
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(ApiStatus.SUCCESS, message, data);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(ApiStatus.SUCCESS, message, null);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message, null);
    }
}
