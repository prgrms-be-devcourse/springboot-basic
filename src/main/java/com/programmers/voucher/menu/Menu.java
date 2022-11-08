package com.programmers.voucher.menu;

import java.util.Arrays;

import static com.programmers.voucher.menu.Message.ERROR_INPUT_MESSAGE;

public enum Menu {
    EXIT("EXIT"),
    CREATE("CREATE"),
    LIST("LIST"),
    ;

    private final String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public static Menu findMenu(String userInput) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getMenu().equals(userInput))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ERROR_INPUT_MESSAGE.getMessage()));
    }

    public String getMenu() {
        return menu;
    }
}
