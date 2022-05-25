package org.devcourse.voucher.core.menu.model;

import org.devcourse.voucher.core.exception.ErrorType;

import java.util.Arrays;

public enum CreateMenuType {
    VOUCHER("1"),
    CUSTOMER("2");

    private String option;

    CreateMenuType(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public static CreateMenuType discriminate(String input) {
        return Arrays.stream(values())
                .filter(type -> type.getOption().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorType.INVALID_COMMAND.message()));
    }

}
