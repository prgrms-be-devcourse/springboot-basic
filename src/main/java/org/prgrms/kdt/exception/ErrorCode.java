package org.prgrms.kdt.exception;

public enum ErrorCode {
    NOT_FOUND(404, "COMMON-ERROR-404"),
    INNER_SERVER_ERROR(500, "INTERNAL-SERVER-ERROR-500");

    private final int status;
    private final String errorCode;

    ErrorCode(int status, String errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
