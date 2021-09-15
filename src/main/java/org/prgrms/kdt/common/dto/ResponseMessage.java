package org.prgrms.kdt.common.dto;

public enum ResponseMessage {
    SUCCESS(200, "성공"),
    FAIL(400, "실패");

    private final int status;
    private final String message;

    ResponseMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


}
