package com.programmers.springbasic.global.common.response.service;

import com.programmers.springbasic.global.common.response.CommonCode;
import com.programmers.springbasic.global.common.response.model.CommonResult;
import com.programmers.springbasic.global.common.response.model.ListResult;
import com.programmers.springbasic.global.common.response.model.SingleResult;

import java.util.List;

public class ResponseFactory {
    // 단일 결과 처리 메소드
    public static <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 복수 결과 처리 메소드
    public static <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    // 결과'만' 반환 (성공)
    public static CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 결과'만' 반환 (실패)
    public static CommonResult getFailResult(int code, String message) {
        CommonResult result = new CommonResult();
        setFailResult(result, code, message);
        return result;
    }

    // API 요청 성공 시 공통 응답 모델을 성공 데이터로 세팅
    private static void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonCode.SUCCESS.getCode());
        result.setMessage(CommonCode.SUCCESS.getMessage());
    }

    // API 요청 성공 시 공통 응답 모델을 실패 데이터로 세팅
    private static void setFailResult(CommonResult result, int code, String message) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
    }
}
