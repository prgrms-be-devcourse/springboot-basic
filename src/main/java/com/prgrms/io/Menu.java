package com.prgrms.io;

import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String value;

    Menu(String value) {
        this.value = value;
    }

    public static Optional<Menu> findBySelectedMenu(String selectedMenu) {
        return Arrays.stream(Menu.values())
                .filter(m -> m.value.equals(selectedMenu))
                .findFirst();
    }

    public boolean isExit() {
        return this == Menu.EXIT;
    }
}
