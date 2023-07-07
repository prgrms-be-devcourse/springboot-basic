package org.weekly.weekly.util;

public enum PrintMessage {
    MANAGE_PROGRAM("=== Manage Program ==="),
    VOUCHER_PROGRAM("=== Voucher Program ==="),
    CUSTOMER_PROGRAM("=== Customer Program ==="),
    CREATE_VOUCHER("바우처를 생성합니다.\n" +
            "입력예시 => "),
    INPUT_MESSAGE("입력하세요: "),
    DISCOUNT_SELECT("할인 종류중 하나를 선택하세요"),
    NO_VOUCHER_DATAS("저장소에 데이터가 없습니다."),
    EMPTY(""),
    CREATE_VOUCHER_SUCCESS("[바우처 생성에 성공]: "),
    FIND_ALL_VOUCHER_SUCCESS("[모든 바우처 조회 성공]: ");

    private final String message;

    PrintMessage(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
