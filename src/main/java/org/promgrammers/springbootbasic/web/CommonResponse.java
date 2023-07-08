package org.promgrammers.springbootbasic.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final String code;
    private final String msg;
    private final T body;

    @Builder
    public CommonResponse(String code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}