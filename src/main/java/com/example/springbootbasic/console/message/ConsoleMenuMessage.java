package com.example.springbootbasic.console.message;

import java.text.MessageFormat;

import static com.example.springbootbasic.util.CharacterUnit.ENTER;

public enum ConsoleMenuMessage {

    VOUCHER_MENU_MESSAGE (MessageFormat.format(
            "=== Voucher Program ==={0}" +
                    "Type **exit** to exit the program.{1}" +
                    "Type **create** to create a new voucher.{2}" +
                    "Type **list** to list all vouchers.{3}{4}",
            ENTER.unit(), ENTER.unit(), ENTER.unit(), ENTER.unit(), ENTER.unit())),

    VOUCHER_CREATE_MENU_MESSAGE(MessageFormat.format(
            "Type **fixed '{'number'}'** to create FixedAmountVoucher => ex) fixed 1000 {0}" +
                    "Type **percent '{'number'}'** to create PercentDiscountVoucher => ex) percent 10 {1}{2}",
            ENTER.unit(), ENTER.unit(), ENTER.unit())),

    CUSTOMER_MENU_MESSAGE(MessageFormat.format(
            "=== Customer Program ==={0}" +
                    "Type **customer-black-list** to exit the program. {1}{2}",
            ENTER.unit(), ENTER.unit(), ENTER.unit()));

    private final String message;

    ConsoleMenuMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
