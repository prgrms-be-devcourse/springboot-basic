package org.prgrms.kdt.exception;

import lombok.Data;

@Data
public class ErrorResult {

    private String code;
    private String message;

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
