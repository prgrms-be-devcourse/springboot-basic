package com.prgms.springbootbasic.util;

import java.util.Arrays;
import java.util.List;

public enum Menu {

    EXIT("exit", "exitCommand"),
    CUSTOMER("customer", "customerCommand"),
    VOUCHER("voucher", "voucherCommand");

    private static final List<Menu> MENU_LIST = Arrays.stream(Menu.values()).toList();
    private final String command;
    private final String commandBeanName;

    Menu(String command, String commandBeanName) {
        this.command = command;
        this.commandBeanName = commandBeanName;
    }

    public String getCommandBeanName() {
        return commandBeanName;
    }

    public static Menu of(String command) {
        return MENU_LIST.stream()
                .filter(m -> m.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_SUCH_MENU.getMessage()));
    }

}
