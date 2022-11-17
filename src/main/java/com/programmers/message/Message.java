package com.programmers.message;

public enum Message {
    GREETING_MESSAGE(System.lineSeparator() +
            "=== Program ===" + System.lineSeparator()
            + "원하시는 명령을 입력해주세요" + System.lineSeparator()
            + "종료 - exit" + System.lineSeparator()
            + "회원 가입 - join" + System.lineSeparator()
            + "회원 조회 - customers" + System.lineSeparator()
            + System.lineSeparator()
            + "바우처 등록 - register" + System.lineSeparator()
            + "바우처 조회 - vouchers" + System.lineSeparator()
            + "바우처 삭제 - delete_v" + System.lineSeparator()
            + System.lineSeparator()
            + "회원 바우처 할당 - assign" + System.lineSeparator()
            + "회원 지갑 조회 - wallet" + System.lineSeparator()
            + "바우처 소유자 검색 - voucher_owner" + System.lineSeparator()
            + "보유한 바우처 제거 - delete_w" + System.lineSeparator()
            + System.lineSeparator()),

    VOUCHER_TYPE_MESSAGE("고정할인 바우처는 F, 정률 할인 바우처는 P를 입력해주세요." + System.lineSeparator()),

    VOUCHER_VALUE_MESSAGE("바우처의 금액을 입력해주세요." + System.lineSeparator()
            + "고정 할인 바우처는 1000~200000, 정률 할인 바우처는 1~100" + System.lineSeparator()),

    VOUCHER_CREATE_SUCCESS("바우처 생성 성공" + System.lineSeparator()),


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
