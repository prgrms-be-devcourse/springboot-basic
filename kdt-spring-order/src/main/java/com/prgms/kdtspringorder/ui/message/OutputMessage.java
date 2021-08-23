package com.prgms.kdtspringorder.ui.message;

public enum OutputMessage {
    VOUCHER_PROGRAM_COMMAND(
        "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.\nType help to see commands again."),
    VOUCHER_ID("voucher id: "),
    VOUCHER_TYPE(", voucher 종류: "),
    DISCOUNT_AMOUNT(", voucher 할인가격(률): "),
    BLACKLIST("=== 고객 블랙리스트 목록 ==="),
    CUSTOMER_ID("customer id: "),
    CUSTOMER_NAME(", customer name: "),
    CUSTOMER_AGE(", customer age: ");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
