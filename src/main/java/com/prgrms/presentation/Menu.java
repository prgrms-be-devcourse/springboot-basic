package com.prgrms.presentation;

import com.prgrms.presentation.message.ErrorMessage;

import java.util.Arrays;

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
