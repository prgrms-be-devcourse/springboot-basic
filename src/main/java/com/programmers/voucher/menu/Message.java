package com.programmers.voucher.menu;

public enum Message {
    GREETING_MESSAGE(System.lineSeparator() +
            "=== Voucher Program ===" + System.lineSeparator()
            + "Type exit to exit the program." + System.lineSeparator()
            + "Type create to create a new voucher." + System.lineSeparator()
            + "Type list to list all vouchers." + System.lineSeparator()),
    ERROR_INPUT_MESSAGE("잘못 입력하셨습니다." + System.lineSeparator()),
    VOUCHER_TYPE_MESSAGE("고정할인 바우처는 F, 정률 할인 바우처는 P를 입력해주세요." + System.lineSeparator()),

    VOUCHER_VALUE_MESSAGE("바우처의 금액을 입력해주세요." + System.lineSeparator()
            + "고정 할인 바우처는 1000~200000, 정률 할인 바우처는 1~100" + System.lineSeparator()),

    VOUCHER_CREATE_SUCCESS("바우처 생성 성공" + System.lineSeparator()),

    VOUCHER_INPUT_ERROR_MESSAGE("입력하신 바우처의 값과 타입을 확인해주세요." + System.lineSeparator()),
    VOUCHER_ID_NOT_FOUND("존재하지 않는 바우처입니다." + System.lineSeparator()),
    ;

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
