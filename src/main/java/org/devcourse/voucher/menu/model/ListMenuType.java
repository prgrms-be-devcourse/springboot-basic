package org.devcourse.voucher.menu.model;

import org.devcourse.voucher.error.ErrorType;

import java.util.Arrays;

public enum ListMenuType {
    VOUCHER("1"),
    BLACKLIST("2"),
    CUSTOMER("3");

    private String option;

    ListMenuType(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public static ListMenuType discriminate(String input) {
        return Arrays.stream(values())
                .filter(type -> type.getOption().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorType.INVALID_COMMAND.message()));
    }
}
