package com.programmers.voucher.io;

public enum Message {
    INTRO_MESSAGE("""
            === Voucher Program ===
                Type **1** to exit the program.
                Type **2* to create a new voucher.
                Type **3** to list all vouchers.
                Type **4** to select a voucher.
                Type **5** to update a voucher.
                Type **6** to delete all vouchers.
                Type **7** to create a new customer.
                Type **8** to select a customer by voucher.
                Type **9** to assign a voucher to customer.
                Type **10** to list all vouchers the customer has."""),
    WRONG_ORDER_MESSAGE("잘못된 입력입니다."),
    REQUEST_VOUCHER_TYPE_MESSAGE("FixedAmountVoucher는 \"1\", PercentDiscountVoucher는 \"2\"를 입력해주세요."),
    REQUEST_DISCOUNT_VALUE_MESSAGE("원하는 할인값을 입력해주세요."),
    REQUEST_VOUCHER_ID("바우처 16자리 번호를 입력해주세요."),
    EMPTY_VOUCHER_MESSAGE("조회되는 voucher가 없습니다."),
    DELETE_ALL_VOUCHERS("모든 바우처가 삭제되었습니다."),
    REQUEST_CUSTOMER_NAME("사용자 이름을 입력해주세요."),
    REQUEST_CUSTOMER_EMAIL("이메일을 입력해주세요."),
    WELCOME_CUSTOMER("님 환영합니다."),
    ASSIGN_VOUCHER("%s님에게 %s 바우처가 할당되었습니다."),
    OWN_VOUCHER("님이 보유한 바우처입니다.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
