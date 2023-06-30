package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
                "Type list to list all vouchers.\n" +
                "Type blacklist to list all blackLists");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printAll(List<Voucher> voucherList) {
        Arrays.stream(voucherList.toArray())
                .forEach(voucher -> {
                    System.out.println("voucher = " + voucher);
                });
    }

    public void printAllBlackList(List<List<String>> blackList) {
        Arrays.stream(blackList.toArray())
                .forEach(customerName -> {
                    System.out.println("customer name = " + customerName);
                });
    }
}
