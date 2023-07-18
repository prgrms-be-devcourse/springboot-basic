package org.prgrms.kdtspringdemo.customer.exception;

public enum CustomerExceptionMessage {
    NOT_FOUND_CUSTOMER_COMMAND_TYPE("알맞는 소비자 기능이 없습니다."),
    CUSTOMER_ID_LOOKUP_FAILED("조회된 소비자 ID가 없습니다."),
    CUSTOMER_NICKNAME_LOOKUP_FAILED("조회된 소비자 닉네임이 없습니다.")
    ;

    private String message;

    CustomerExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] : " + message;
    }
}
