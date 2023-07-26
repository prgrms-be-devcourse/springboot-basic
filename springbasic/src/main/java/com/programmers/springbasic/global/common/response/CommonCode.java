package com.programmers.springbasic.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonCode {
    SUCCESS(0, "성공했습니다."),
    FAIL(-1, "실패했습니다.");

    private int code;
    private String message;
}
