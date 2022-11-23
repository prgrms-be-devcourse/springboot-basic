package com.programmers.kwonjoosung.springbootbasicjoosung.console.message;

public enum RequestMessage {
    INPUT_COMMAND("command >> "),
    INPUT_VOUCHER_TYPE("voucherType >> "),
    INPUT_DISCOUNT("discount >> "),
    INPUT_VOUCHER_ID("voucherId >> "),
    INPUT_CUSTOMER_ID("customerId >> "),
    INPUT_CUSTOMER_NAME("name >>");

    private final String message;

    RequestMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
