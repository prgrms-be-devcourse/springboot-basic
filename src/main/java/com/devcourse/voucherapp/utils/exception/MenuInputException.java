package com.devcourse.voucherapp.utils.exception;

import static java.text.MessageFormat.format;

public class MenuInputException extends RuntimeException {

    private static final String NOT_EXIST_MENU_MESSAGE = "입력하신 메뉴는 없는 메뉴입니다.";

    public MenuInputException(String menuOption) {
        super(format("{0} | 입력 : {1}", NOT_EXIST_MENU_MESSAGE, menuOption));
    }
}
