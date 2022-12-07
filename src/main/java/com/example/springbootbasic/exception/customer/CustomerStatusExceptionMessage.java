package com.example.springbootbasic.exception.customer;

public enum CustomerStatusExceptionMessage {

    CUSTOMER_STATUS_FIND_EXCEPTION("존재하지 않는 고객 상태 타입입니다.");

    private final String message;

    CustomerStatusExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
