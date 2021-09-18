package org.prgrms.devcourse;


import java.util.Arrays;

public enum VoucherProgramMenu {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    FIXED_AMOUNT_DISCOUNT_VOUCHER("1"),
    PERCENT_DISCOUNT_VOUCHER("2"),
    BLACK_LIST("blacklist"),
    CUSTOMER_LIST("customerlist");

    private String menu;

    VoucherProgramMenu(String menu) {
        this.menu = menu;
    }


    public static VoucherProgramMenu findByUserInput(String userInput) {
        return Arrays.stream(values())
                .filter(m -> m.menu.equals(userInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 메뉴 입니다."));
    }
}
