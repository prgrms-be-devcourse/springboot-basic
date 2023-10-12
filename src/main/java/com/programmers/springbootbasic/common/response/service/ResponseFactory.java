package com.programmers.springbootbasic.common.response.service;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.model.ListResult;

import java.util.List;

public class ResponseFactory {
    public static CommonResult getSuccessResult() {
        return new CommonResult(true, "완료");
    }

    public static CommonResult getFailResult(String message) {
        return new CommonResult(false, message);
    }

    public static <T> ListResult<T> getListResult(List<T> data) {
        return new ListResult<>(data);
    }
}
