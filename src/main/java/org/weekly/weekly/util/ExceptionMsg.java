package org.weekly.weekly.util;

public enum ExceptionMsg {
    EMPTY("사용자가 아무 값도 입력하지 않았습니다."),
    NOT_INPUT_FORMAT("입력 형식에 맞지 않습니다."),
    NOT_MENU("해당 메뉴는 존재하지 않습니다."),
    NOT_NUMBER_FORMAT("숫자 형식이 아닙니다"),
    NOT_NATURAL_NUMBER("자연수가 아닙니다."),
    NOT_DISCOUNT("할인 종류가 아닙니다."),
    EXPIRATION_ERROR("유효기간이 등록기간보다 빠릅니다."),
    VOUCHER_EXIST("이미 존재하는 바우처입니다.");


    private String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
