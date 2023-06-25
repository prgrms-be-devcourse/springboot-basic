package org.weekly.weekly.util;

public enum PrintMsg {
    PROGRAM("=== Voucher Program ==="),
    CREATE_VOUCHER("바우처를 생성합니다.\n" +
            "입력 예시\n" +
            "    1. 고정: 바우처 할인 금액,바우처 유효 개월 수\n" +
            "    2. 퍼센트: 바우처 퍼센트(0~100으로만), 바우처 유효 개월 수\n" +
            "(,)콤마를 기준으로 입력하세요 :"),
    DISCOUNT_SELECT("할인 종류중 하나를 선택하세요"),
    NO_VOUCHER_DATAS("저장소에 데이터가 없습니다."),
    EMPTY("");

    private String msg;

    PrintMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
