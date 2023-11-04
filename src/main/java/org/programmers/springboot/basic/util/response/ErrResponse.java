package org.programmers.springboot.basic.util.response;

import lombok.Getter;

@Getter
public class ErrResponse {

    private final ErrCode code;
    private final String message;

    public ErrResponse(ErrCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
