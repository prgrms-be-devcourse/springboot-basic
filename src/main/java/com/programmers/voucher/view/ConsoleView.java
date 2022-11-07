package com.programmers.voucher.view;

import com.programmers.voucher.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class ConsoleView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
    }


    @Override
    public String getUserCommand() {
        return scanner.nextLine().trim();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
        System.out.println("============================");
    }
}
