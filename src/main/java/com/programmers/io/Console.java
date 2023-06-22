package com.programmers.io;

import org.springframework.stereotype.Component;

@Component
public class Console implements Output {
    private static final String MENU_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.";

    @Override
    public void printMenu() {
        System.out.println(MENU_MESSAGE);
    }
}
