package org.weekly.weekly.util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ManageMenu implements Menu {
    EXIT("Type exit to exit the program."),
    VOUCHER("Type create to create a new voucher."),
    CUSTOMER("Type list to list all vouchers.");

    private final String printMessage;
    private static final Map<String, ManageMenu> MANAGE_MENU_MAP;

    static {
        MANAGE_MENU_MAP = new ConcurrentHashMap<>();
        Arrays.stream(ManageMenu.values())
                .peek(manageMenu -> MANAGE_MENU_MAP.put(manageMenu.name(), manageMenu));
    }

    ManageMenu(String printMessage) {
        this.printMessage = printMessage;
    }

    public static ManageMenu getMenu(String userInput) {
        if (MANAGE_MENU_MAP.containsKey(userInput)) {
            return MANAGE_MENU_MAP.get(userInput);
        }
        throw new RuntimeException(ExceptionMsg.NOT_MENU.getMsg());
    }

    @Override
    public String printMessage() {
        return printMessage;
    }
}
