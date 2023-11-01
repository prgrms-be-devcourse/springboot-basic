package org.prgrms.vouchermanager.common;

public record ApiResponse(
        ApiStatus status,
        String message,
        Object data
) {
    public static ApiResponse success(Object data) {
        return new ApiResponse(ApiStatus.SUCCESS, "", data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message, null);
    }
}
