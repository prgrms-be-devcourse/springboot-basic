package com.prgrms.io;

import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    EXIT,
    CREATE,
    LIST;

    public static Menu findByMenu(String menu) {
        return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(menu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }
}
