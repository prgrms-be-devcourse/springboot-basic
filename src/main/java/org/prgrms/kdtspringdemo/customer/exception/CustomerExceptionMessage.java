package org.prgrms.kdtspringdemo.customer.exception;

public enum CustomerExceptionMessage {
    NOT_FOUND_CUSTOMER_COMMAND_TYPE("알맞는 소비자 기능이 없습니다."),
    FAILED_CUSTOMER_SAVE_QUERY("소비자 저장 쿼리를 실패 하였습니다.");

    private String message;

    CustomerExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] : " + message;
    }
}
