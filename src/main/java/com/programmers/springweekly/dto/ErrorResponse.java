package com.programmers.springweekly.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final int errorCode;
    private final String errorMsg;

}
