package org.prgrms.orderapp.io;

import org.prgrms.orderapp.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void vouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void inputError(String input) {
        System.out.println(MessageFormat.format("Invalid input `{0}`", input));
    }

}
