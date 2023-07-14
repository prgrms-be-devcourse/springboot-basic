package org.weekly.weekly.util;

import org.weekly.weekly.customer.exception.CustomerException;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CustomerMenu implements Menu {
    EXIT("[Type: exit] to exit the program."),
    CREATE("[Type: create] to create a new voucher."),
    DELETE("[Type: list] to list all vouchers."),
    DELETE_ALL("[Type: delete_all] 모든 유저 삭제"),
    FIND_ALL("[Type: find_all] 모든 유저 검색"),
    FIND_DETAIL("[Type: find_detail] 유저 상세 검색"),
    UPDATE("[Type: update] 유저 정보 업데이트");

    private final String printMessage;
    private static final Map<String, CustomerMenu> CUSTOMER_MENU_MAP;
    static {
        CUSTOMER_MENU_MAP = new ConcurrentHashMap<>();
        Arrays.stream(CustomerMenu.values())
                .forEach(customerMenu -> CUSTOMER_MENU_MAP.put(customerMenu.name(), customerMenu));
    }

    CustomerMenu(String printMessage) {
        this.printMessage = printMessage;
    }

    public static CustomerMenu getMenu(String userInput) {
        if (CUSTOMER_MENU_MAP.containsKey(userInput)) {
            return CUSTOMER_MENU_MAP.get(userInput);
        }
        throw new CustomerException(ExceptionMsg.NOT_MENU);
    }

    @Override
    public String printMessage() {
        return printMessage;
    }
}
