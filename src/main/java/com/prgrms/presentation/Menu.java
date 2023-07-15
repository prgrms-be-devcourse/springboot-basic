package com.prgrms.presentation;

import com.prgrms.presentation.message.ErrorMessage;

import java.util.Arrays;

public enum Menu {
    EXIT,
    CREATE,
    LIST,
    GIVE_VOUCHER,
    TAKE_VOUCHER,
    CUSTOMER_LIST,
    VOUCHER_LIST;

    public static Menu findByMenu(String menu) {
        return Arrays.stream(values())
                .filter(m -> m.name().replace("_", " ").equalsIgnoreCase(menu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }
}
