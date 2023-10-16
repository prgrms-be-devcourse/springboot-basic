package org.prgrms.prgrmsspring.exception;

public enum ExceptionMessage {
    NOT_FOUND_VOUCHER("해당 Voucher를 찾을 수 없습니다."),
    NOT_FOUND_FILE("해당 파일을 찾을 수 없습니다.");
    private final String message;


    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
