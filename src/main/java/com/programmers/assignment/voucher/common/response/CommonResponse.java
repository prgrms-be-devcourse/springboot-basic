package com.programmers.assignment.voucher.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class CommonResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public CommonResponse(T result) {
        this.result = result;
        serverDatetime = LocalDateTime.now();
    }

    public CommonResponse(ResponseMessage responseCode) {
        serverDatetime = LocalDateTime.now();
    }

}
