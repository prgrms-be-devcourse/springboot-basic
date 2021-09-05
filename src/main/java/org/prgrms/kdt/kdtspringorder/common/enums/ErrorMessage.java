package org.prgrms.kdt.kdtspringorder.common.enums;

public enum ErrorMessage {

    VOUCHER_NOT_FOUND_MESSAGE("Can not find a voucher"),
    CUSTOMER_NOT_FOUND_MESSAGE("Can not find a customer");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
