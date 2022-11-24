package com.programmers.commandline.global.entity;

import com.programmers.commandline.global.util.Verification;

import java.util.Arrays;

public enum Menu {
    EXIT(1),
    VOUCHER_CREATE(2),
    VOUCHER_LIST(3),
    BLACK_CONSUMER_LIST(4),
    CONSUMER(5);

    private final int code;

    Menu(int code) {
        this.code = code;
    }

    public static Menu ofMenu(String input) {

        int code = toCode(input);

        return Arrays.stream(Menu.values())
                .filter(menu -> menu.code == code)
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException("잘못된 메뉴 선택입니다.");
                });
    }

    private static int toCode(String input) {
        Verification.validateParseToInt(input);
        return Integer.parseInt(input);
    }
}
