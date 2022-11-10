package com.program.voucher.model;

import com.program.voucher.io.Input;
import com.program.voucher.io.Output;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);
    static final String NEWLINE = System.lineSeparator();

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public String input(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    @Override
    public void menuView() {
        System.out.println("=== Voucher Program ===" + NEWLINE +
                "Type exit to exit the program." + NEWLINE +
                "Type create to create a new voucher." + NEWLINE +
                "Type list to list all vouchers." + NEWLINE +
                "Type blacklist to view all blacklist customer.");
    }

    @Override
    public void errorMessage(String message) {
        System.out.println(message + "\n");
    }

    @Override
    public void successMessage(String message) {
        System.out.println(message + "\n");
    }

    @Override
    public void allVoucherView(List<Voucher> vouchers) {
        if (vouchers.size() == 0) {
            System.out.println("empty\n");
            return;
        }
        vouchers.forEach(value -> {
            String voucher = value.getVoucherType() + " voucher - " + value.getVoucherDiscount();
            System.out.println(value.getVoucherId() + " : " + voucher);
        });
        System.out.println();
    }

    @Override
    public void customerBlackListView(List<String> blackList) {
        if (blackList.size() == 0) {
            System.out.println("empty\n");
            return;
        }
        System.out.println(blackList.stream().reduce("--------------------", (a, b) -> a + "\n" + b));
        System.out.println("--------------------");
    }
}
