package com.programmers.commandline.global.entity;

import com.programmers.commandline.global.aop.LogAspect;

import java.util.Arrays;

public enum Menu {
    EXIT(1),
    VOUCHER_CREATE(2),
    VOUCHER_LIST(3),
    BLACK_CONSUMER_LIST(4),
    ERROR(5);

    private static final int BAD_REQUEST = -1;

    private final int code;

    Menu(int code) {
        this.code = code;
    }

    public static Menu ofMenu(String input) { // exit, create, list -> 123asfdewt
        LogAspect.getLogger().info("Menu selectMenu 실행");
        int code = toCode(input);
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.code == code)
                .findFirst()
                .orElse(ERROR);

    }

    private static int toCode(String input) {
        LogAspect.getLogger().info("Menu toCode 실행");
        try {
            return Integer.parseInt(input);
        } catch (RuntimeException e) {
            return BAD_REQUEST;
        }
    }
}
