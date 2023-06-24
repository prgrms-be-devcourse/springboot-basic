package com.prgrms.io;

import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    EXIT,
    CREATE,
    LIST;

    public static Optional<Menu> findBySelectedMenu(String selectedMenu) {
        return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(selectedMenu))
                .findFirst();
    }

    public boolean isExit() {
        return this == Menu.EXIT;
    }
}
