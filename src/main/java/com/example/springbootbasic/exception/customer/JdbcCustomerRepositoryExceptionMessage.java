package com.example.springbootbasic.exception.customer;

public enum JdbcCustomerRepositoryExceptionMessage {
    CUSTOMER_STATUS_NULL_EXCEPTION("고객 상태에 null이 들어올 수 없습니다.");

    private final String message;

    JdbcCustomerRepositoryExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}