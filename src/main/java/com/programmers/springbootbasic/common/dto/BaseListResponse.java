package com.programmers.springbootbasic.common.dto;

import java.util.List;

public class BaseListResponse<T> {

    private final List<T> data;

    public BaseListResponse(List<T> data) {
        this.data = data;
    }

    public static <T> BaseListResponse<T> from(List<T> data) {
        return new BaseListResponse<>(data);
    }
}
