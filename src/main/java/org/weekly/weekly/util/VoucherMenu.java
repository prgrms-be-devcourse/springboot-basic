package org.weekly.weekly.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum VoucherMenu {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers.");

    private String input;
    private String printMsg;

    private final static Map<String, VoucherMenu> VOUCHER_MENU_MAP;
    static {
        VOUCHER_MENU_MAP = new ConcurrentHashMap<>();
        for(VoucherMenu menu : VoucherMenu.values()) {
            VOUCHER_MENU_MAP.put(menu.input, menu);
        }
    }

    VoucherMenu(String input, String printMsg) {
        this.input = input;
        this.printMsg = printMsg;
    }

    public static VoucherMenu getMenu(String userInput) {
        if (VOUCHER_MENU_MAP.containsKey(userInput)) {
            return VOUCHER_MENU_MAP.get(userInput);
        }
        throw new RuntimeException("해당 메뉴는 존재하지 않습니다.");
    }

}