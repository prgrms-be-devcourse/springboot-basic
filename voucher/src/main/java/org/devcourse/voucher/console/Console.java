package org.devcourse.voucher.console;

import java.util.Scanner;

public class Console implements Input, Output{
    public static final String CREATE = "create";
    public static final String LIST = "list";
    public static final String EXIT = "exit";
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void printMenu() {
        System.out.print("== Voucher Program ==\nType exit to exit the program.\nType create to create a new voucher.\nType list ot list all vouchers.\n>> ");
    }
    @Override
    public String getOption() {
        return sc.next();
    }


}
