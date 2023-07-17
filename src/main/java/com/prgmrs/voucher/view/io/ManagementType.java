package com.prgmrs.voucher.view.io;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public enum ManagementType {
    EXIT_THE_LOOP("Type '0' to exit the program."),
    CREATE_USER("Type '1' to create a user."),
    FIXED_AMOUNT_VOUCHER("Type '2' to create a voucher with fixed amount."),
    PERCENT_DISCOUNT_VOUCHER("Type '3' to create a voucher with percent discount."),
    ASSIGN_VOUCHER("Type '4' to assign voucher to a user."),
    REMOVE_VOUCHER("Type '5' to deallocate voucher from a user."),
    SHOW_ALL_VOUCHERS("Type '6' to list all vouchers."),
    SHOW_USER_WALLET("Type '7' to list vouchers a specific user has."),
    SHOW_VOUCHER_OWNER("Type '8' to show the owner of specific voucher."),
    SHOW_BLACKLIST("Type '9' to print out the blacklist.");

    private final String value;

    ManagementType(String value) {
        this.value = value;
    }

    public static ManagementType of(String value) {
        try {
            int convertedValue = Integer.parseInt(value);
            if (convertedValue >= 0 && convertedValue < ManagementType.values().length) {
                return ManagementType.values()[convertedValue];
            }
            throw new WrongRangeFormatException("no such option exists");
        } catch (NumberFormatException e) {
            throw new WrongRangeFormatException("input must be number");
        }
    }

    public String getValue() {
        return value;
    }
}
