package com.prgrms.presentation;

import com.prgrms.presentation.message.ErrorMessage;
import java.util.Arrays;

public enum Menu {
    EXIT("exitCommand"),
    CREATE("createCommand"),
    LIST("listCommand"),
    GIVE_VOUCHER("giveVoucherCommand"),
    TAKE_VOUCHER("takeVoucherCommand"),
    CUSTOMER_LIST("customerListCommand"),
    VOUCHER_LIST("voucherListCommand");

    private final String beanName;

    Menu(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public static Menu findByMenu(String menu) {
        return Arrays.stream(values())
                .filter(m -> m.name().replace("_", " ").equalsIgnoreCase(menu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessage.INVALID_SELECTION.getMessage()));
    }

}
