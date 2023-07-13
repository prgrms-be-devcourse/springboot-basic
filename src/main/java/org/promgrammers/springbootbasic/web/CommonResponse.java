package org.promgrammers.springbootbasic.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {

    private final int status;
    private final String message;
    private final T body;

    @Builder
    public CommonResponse(int status, String message, T body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }
}