package org.weekly.weekly.util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CustomerMenu implements Menu {
    EXIT("Type exit to exit the program."),
    CREATE("Type create to create a new voucher."),
    DELETE("Type list to list all vouchers."),
    SEARCH_ALL("Type: search all 모든 유저 검색"),
    SEARCH_DETAIL_TO_EMAIL("Type: search detail to email 유저 상세 검색");

    private final String printMessage;
    private static final Map<String, CustomerMenu> CUSTOMER_MENU_MAP;
    static {
        CUSTOMER_MENU_MAP = new ConcurrentHashMap<>();
        Arrays.stream(CustomerMenu.values())
                .peek(customerMenu -> CUSTOMER_MENU_MAP.put(customerMenu.name(), customerMenu));
    }

    CustomerMenu(String printMessage) {
        this.printMessage = printMessage;
    }

    public static CustomerMenu getMenu(String userInput) {
        if (CUSTOMER_MENU_MAP.containsKey(userInput)) {
            return CUSTOMER_MENU_MAP.get(userInput);
        }
        throw new RuntimeException(ExceptionMsg.NOT_MENU.getMsg());
    }

    public String getPrintMessage() {
        return printMessage;
    }
}
