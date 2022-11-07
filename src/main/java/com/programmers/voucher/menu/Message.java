package com.programmers.voucher.menu;

public enum Message {
    GREETING_MESSAGE(System.lineSeparator() +
            "=== Voucher Program ===" + System.lineSeparator()
            + "Type exit to exit the program." + System.lineSeparator()
            + "Type create to create a new voucher." + System.lineSeparator()
            + "Type list to list all vouchers." + System.lineSeparator()),

    VOUCHER_TYPE_MESSAGE("고정할인 바우처는 F, 정률 할인 바우처는 P를 입력해주세요." + System.lineSeparator()),

    VOUCHER_VALUE_MESSAGE("바우처의 금액을 입력해주세요." + System.lineSeparator()
            + "고정 할인 바우처는 1000~200000, 정률 할인 바우처는 1~100" + System.lineSeparator()),

    VOUCHER_CREATE_SUCCESS("바우처 생성 성공" + System.lineSeparator()),

    INPUT_ERROR_MESSAGE("잘못 입력하셨습니다." + System.lineSeparator()),
    ;

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
