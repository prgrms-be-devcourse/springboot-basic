package com.example.springbootbasic.exception.customer;

public enum CustomerServiceExceptionMessage {
    CUSTOMER_NULL_EXCEPTION("고객이 null일 수 없습니다.");

    private final String message;

    CustomerServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
