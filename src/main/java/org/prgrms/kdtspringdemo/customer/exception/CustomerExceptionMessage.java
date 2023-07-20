package org.prgrms.kdtspringdemo.customer.exception;

public enum CustomerExceptionMessage {
    NOT_FOUND_CUSTOMER_COMMAND_TYPE(404, "알맞는 소비자 기능이 없습니다."),
    CUSTOMER_ID_LOOKUP_FAILED(404, "조회된 소비자 ID가 없습니다."),
    CUSTOMER_NICKNAME_LOOKUP_FAILED(404, "조회된 소비자 닉네임이 없습니다.")
    ;

    private final int code;
    private final String message;

    CustomerExceptionMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return code + " ERROR : " + message;
    }
}
