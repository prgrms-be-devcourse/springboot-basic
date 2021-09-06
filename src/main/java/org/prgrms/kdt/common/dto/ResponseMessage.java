package org.prgrms.kdt.common.dto;

public enum ResponseMessage {
    SUCCESS("성공"),
    FAIL("실패");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
