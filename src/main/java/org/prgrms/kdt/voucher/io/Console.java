package org.prgrms.kdt.voucher.io;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console {

    private final Scanner scanner = new Scanner(System.in);

    public String input(String input) {
        System.out.println(input);
        return scanner.nextLine();
    }

    public void menu() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printAll(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println("voucher = " + voucher);
        }
    }
}
