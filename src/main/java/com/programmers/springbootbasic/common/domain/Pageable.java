package com.programmers.springbootbasic.common.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record Pageable(
        @NotNull @Min(value = 0, message = INVALID_PAGE) int page,
        @NotNull @Min(value = 1, message = INVALID_SIZE) int size
) {
    private static final String INVALID_PAGE = "페이지는 0보다 커야합니다.";
    private static final String INVALID_SIZE = "사이즈는 0보다 커야합니다.";

    public long getOffSet() {
        return (long) page * (long) size;
    }
}
