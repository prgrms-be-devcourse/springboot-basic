package org.devcourse.voucher.model.menu;

import java.util.Arrays;

public enum CreateMenuType {
    VOUCHER("1"),
    CUSTOMER("2"),
    NONE("");

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
                .orElse(NONE);
    }

}
