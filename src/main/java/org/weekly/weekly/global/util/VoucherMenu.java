package org.weekly.weekly.global.util;

import org.weekly.weekly.global.handler.ExceptionCode;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum VoucherMenu implements Menu {
    EXIT("Type exit to exit the program."),
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers.");

    private final String printMessage;

    private static final Map<String, VoucherMenu> VOUCHER_MENU_MAP;

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
        throw new VoucherException(ExceptionCode.NOT_MENU);
    }

    @Override
    public String printMessage() {
        return printMessage;
    }
}