package com.programmers.voucher.constant;

public class ErrorMessage {
    public static final String INVALID_COMMAND = "존재하지 않는 명령어입니다.";
    public static final String INVALID_DISCOUNT_AMOUNT = "할인 값은 양수만 가능합니다.";
    public static final String INVALID_DISCOUNT_PERCENT = "할인율은 100을 넘을 수 없습니다.";
    public static final String NOT_FOUND_VOUCHER = "존재하는 바우처가 없습니다.";
    public static final String NOT_FOUND_CUSTOMER = "존재하는 고객이 없습니다.";
    public static final String EXISTED_NICKNAME = "이미 존재하는 닉네임입니다.";

    private ErrorMessage() {}
}
