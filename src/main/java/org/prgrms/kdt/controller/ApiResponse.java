package org.prgrms.kdt.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private final boolean success;
    private final T response;
    private final ApiError error;

    public ApiResponse(boolean success, T response, ApiError error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }

    public static <T> ApiResponse<T> OK(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> ERROR(Throwable throwable, HttpStatus status) {
        return new ApiResponse<>(false, null, new ApiError(throwable, status));
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResponse() {
        return response;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("success", success)
            .append("response", response)
            .append("success", success)
            .toString();
    }
}
