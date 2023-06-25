package org.devcourse.voucher.console;

import java.util.Scanner;

public class Console implements Input, Output{

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void printMenu() {
        String menuString = """
        == Voucher Program ==
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list ot list all vouchers.
        """.trim();
        System.out.println(menuString);
    }

    @Override
    public String input() {
        return sc.next();
    }
}
