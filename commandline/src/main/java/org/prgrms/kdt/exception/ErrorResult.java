package org.prgrms.kdt.exception;

import lombok.Data;

@Data
public class ErrorResult {

    private int status;
    private String message;

    public ErrorResult(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
