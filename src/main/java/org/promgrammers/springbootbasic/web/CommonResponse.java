package org.promgrammers.springbootbasic.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {

    private final int status;
    private final String msg;
    private final T body;

    @Builder
    public CommonResponse(int status, String msg, T body) {
        this.status = status;
        this.msg = msg;
        this.body = body;
    }
}