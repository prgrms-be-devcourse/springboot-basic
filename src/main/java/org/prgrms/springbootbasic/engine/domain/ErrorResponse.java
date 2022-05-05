package org.prgrms.springbootbasic.engine.domain;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;

public class ErrorResponse {
    private int status;
    private String message;
    private String code;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
