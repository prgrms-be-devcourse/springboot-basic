package com.programmers.voucher.exception;

public class MenuTypeFormatException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 올바른 메뉴 형식을 입력해주세요. ";

    public MenuTypeFormatException() {
        super(MESSAGE);
    }
}
