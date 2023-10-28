package com.programmers.springbootbasic.common.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CommonResult<T> {
    private final boolean isSuccess;
    private final T data;

    private CommonResult(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public static CommonResult<String> getSuccessResult() {
        return new CommonResult<>(true, "완료");
    }

    public static CommonResult<String> getFailResult(String message) {
        return new CommonResult<>(false, message);
    }

    public static <T> CommonResult<T> getSingleResult(T data) {
        return new CommonResult<>(true, data);
    }

    public static <T> CommonResult<List<T>> getListResult(List<T> data) {
        return new CommonResult<>(true, data);
    }
}
