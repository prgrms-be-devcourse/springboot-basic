package org.weekly.weekly.util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum VoucherMenu implements Menu {
    EXIT("Type exit to exit the program."),
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers.");
    private final String printMessage;

    private final static Map<String, VoucherMenu> VOUCHER_MENU_MAP;
    static {
        VOUCHER_MENU_MAP = new ConcurrentHashMap<>();
        Arrays.stream(VoucherMenu.values())
                .forEach(menu -> VOUCHER_MENU_MAP.put(menu.name(), menu));
    }

    VoucherMenu(String printMsg) {
        this.printMessage = printMsg;
    }

    public static VoucherMenu getMenu(String userInput) {
        if (VOUCHER_MENU_MAP.containsKey(userInput)) {
            return VOUCHER_MENU_MAP.get(userInput);
        }
        throw new RuntimeException(ExceptionMsg.NOT_MENU.getMsg());
    }

    @Override
    public String printMessage() {
        return printMessage;
    }
}