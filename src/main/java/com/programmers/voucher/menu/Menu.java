package com.programmers.voucher.menu;

import java.util.Arrays;

import static com.programmers.message.ErrorMessage.ERROR_INPUT_MESSAGE;

public enum Menu {
    EXIT,
    CREATE,
    LIST,
    ;

    Menu() {
    }

    public static Menu findMenu(String userInput) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.name().equals(userInput))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ERROR_INPUT_MESSAGE.getMessage()));
    }
}
