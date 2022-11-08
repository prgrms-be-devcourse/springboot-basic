package com.programmers.voucher.io;

public enum Message {
    INTRO_MESSAGE("=== Voucher Program ===\n" +
            "    Type **exit** to exit the program.\n" +
            "    Type **create** to create a new voucher.\n" +
            "    Type **list** to list all vouchers."),
    WRONG_ORDER_MESSAGE("잘못된 입력입니다."),
    REQUEST_VOUCHER_TYPE_MESSAGE("FixedAmountVoucher와 PercentDiscountVoucher 중 선택해주세요."),
    REQUEST_DISCOUNT_VALUE_MESSAGE("원하는 할인값을 입력해주세요."),
    EMPTY_VOUCHER_MESSAGE("조회되는 voucher가 없습니다.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
