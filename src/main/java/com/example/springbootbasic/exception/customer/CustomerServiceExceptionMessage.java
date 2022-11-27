package com.example.springbootbasic.exception.customer;

public enum CustomerServiceExceptionMessage {
    CUSTOMER_STATUS_NULL_EXCEPTION("고객 상태에 null이 들어올 수 없습니다."),
    CUSTOMER_VOUCHER_NULL_EXCEPTION("고객 바우처 목록에 null을 저장할 수 없습니다."),
    CUSTOMER_NULL_EXCEPTION("고객이 null일 수 없습니다.");

    private final String message;

    CustomerServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
