package com.programmers.vouchermanagement.utils;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputManager {

    public void printCommandMenu() {
        System.out.println("=== Voucher Program === \n" +
                "Type exit to exit the program. \n" +
                "Type create to create a new voucher. \n" +
                "Type list to list all vouchers. \n");
    }

    public void printVoucherTypeMenu() {
        System.out.println("=== Create A New Voucher === \n" +
                "Type fixed to create a fixed amount voucher. \n" +
                "Type percent to create a percent discount voucher. \n");
    }

    public void printDiscountRequest() {
        System.out.println("Type a discount amount. \n");
    }

    public void printExit() {
        System.out.println("You have typed exit. Exit the program. \n");
    }

    public void printEnterAgain(String message) {
        System.out.println(message + "Please enter it again. \n");
    }

    public void printReturnMain(String message) {
        System.out.println(message + "Return to main menu. \n");
    }
}
