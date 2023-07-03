package com.programmers.springbasic.domain.io;

import com.programmers.springbasic.domain.customer.view.CustomerMenu;
import com.programmers.springbasic.domain.voucher.view.VoucherMenu;

import java.util.List;
import java.util.Scanner;

public class IOConsole {
    private static final Scanner sc = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println("===== Main Menu =====");
        System.out.println("1. Customer Service");
        System.out.println("2. Voucher Service");
    }

    public void showVoucherMenu() {
        StringBuilder sb = new StringBuilder();

        sb.append(VoucherMenu.MAIN_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.EXIT_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.CREATE_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.LIST_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.READ_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.UPDATE_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.DELETE_MENU_MESSAGE.getMessage() + "\n")
                .append(VoucherMenu.SHOW_MENU_MESSAGE.getMessage() + "\n");

        System.out.println(sb);
    }

    public void showCustomerMenu() {
        StringBuilder sb = new StringBuilder();

        sb.append(CustomerMenu.MAIN_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.EXIT_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.CREATE_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.LIST_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.READ_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.UPDATE_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.DELETE_MENU_MESSAGE.getMessage() + "\n")
                .append(CustomerMenu.SHOW_MENU_MESSAGE.getMessage() + "\n");

        System.out.println(sb);
    }

    public String getInput() {
        String input = sc.nextLine();

        return input;
    }

    public void printSingleOutput(String output) {
        System.out.println(output);
    }

    public void printListOutput(List<String> outputs) {
        StringBuilder sb = new StringBuilder();

        for (String output : outputs) {
            sb.append(output + "\n");
        }

        System.out.println(sb);
    }
}
