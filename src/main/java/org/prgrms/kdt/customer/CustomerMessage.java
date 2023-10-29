package org.prgrms.kdt.customer;

public enum CustomerMessage {
    EXCEPTION_NOT_EXIST_CUSTOMER("존재하지 않는 회원입니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    CustomerMessage(String message) {
        this.message = message;
    }
}
