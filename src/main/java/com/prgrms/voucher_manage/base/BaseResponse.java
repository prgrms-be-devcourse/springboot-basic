package com.prgrms.voucher_manage.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private final Boolean isSuccess;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.isSuccess = true;
        this.message = "성공";
        this.result = result;
    }

    public BaseResponse() {
        this.isSuccess = true;
        this.message = "성공";
    }

    // 요청에 실패한 경우
    public BaseResponse(String errorMessage) {
        this.isSuccess = false;
        this.message = errorMessage;
    }
}
