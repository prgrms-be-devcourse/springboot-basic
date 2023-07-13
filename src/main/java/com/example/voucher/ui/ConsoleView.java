package com.example.voucher.ui;

import com.example.voucher.domain.Voucher;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements Input, Output {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String readInput() {
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input;
    }

    @Override
    public double readVoucherInput() {
        return Double.parseDouble(scanner.nextLine());
    }

    private void validateEmptyInput(String input) {
        if (input.length() == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void printProgramInfo() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
    }

    @Override
    public void requestVoucherAmount() {
        System.out.print("Enter voucher amount: ");
    }

    @Override
    public void printVoucherInfoList(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }
}
