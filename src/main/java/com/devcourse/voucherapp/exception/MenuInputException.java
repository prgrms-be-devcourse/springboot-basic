package com.devcourse.voucherapp.exception;

import static java.text.MessageFormat.format;

public class MenuInputException extends RuntimeException {

    private static final String NOT_EXIST_MENU_MESSAGE = "존재하지 않는 메뉴입니다. 다시 선택해주세요.";

    public MenuInputException(String menuOption) {
        super(format("{0} | 현재 입력 : {1}", NOT_EXIST_MENU_MESSAGE, menuOption));
    }
}
