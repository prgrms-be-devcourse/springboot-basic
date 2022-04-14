package org.prgrms.kdt.view;

import java.util.Arrays;

public enum Menu {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    NONE("none");

    private final String keyword;

    Menu(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public static Menu findOf(String s) {
        return Arrays.stream(Menu.values())
            .filter(menu -> menu.getKeyword().equals(s))
            .findFirst()
            .orElse(Menu.NONE);
    }

}
