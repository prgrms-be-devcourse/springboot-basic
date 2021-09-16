package org.prgrms.devcourse.domain;


public enum VoucherProgramMenu {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    FIXED_AMOUNT_DISCOUNT_VOUCHER("1"),
    PERCENT_DISCOUNT_VOUCHER("2"),
    BLACK_LIST("blacklist"),
    INVALID("");

    private String userInput;

    VoucherProgramMenu(String userInput) {
        this.userInput = userInput;
    }


    public static VoucherProgramMenu findByUserInput(String userInput) {
        for (VoucherProgramMenu function : VoucherProgramMenu.values()) {
            if (function.userInput.equals(userInput)) {
                return function;
            }
        }

        return INVALID;
    }
}
