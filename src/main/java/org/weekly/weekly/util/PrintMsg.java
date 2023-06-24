package org.weekly.weekly.util;

public enum PrintMsg {
    PROGRAM("=== Voucher Program ==="),
    CREATE_VOUCHER("바우처를 생성합니다.\n (바우처 금액, 바우처 유효기간) 을 입력하세요"),
    DISCOUNT_SELECT("할인 종류중 하나를 선택하세요"),

    EMPTY("");

    private String msg;

    PrintMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
