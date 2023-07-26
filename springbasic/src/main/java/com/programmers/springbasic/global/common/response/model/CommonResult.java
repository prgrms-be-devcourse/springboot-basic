package com.programmers.springbasic.global.common.response.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
    private boolean success;    // 응답 성공 여부
    private int code;   // 응답 코드
    private String message; // 응답 메시지
}
