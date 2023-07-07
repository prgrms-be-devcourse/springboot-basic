package com.example.springbootbasic.io;

import com.example.springbootbasic.voucher.FixedAmountVoucher;
import com.example.springbootbasic.voucher.PercentDiscountVoucher;
import com.example.springbootbasic.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleVoucherService implements Input, Output {
    Scanner scanner = new Scanner(System.in);

    public static void print(String text) {
        System.out.println(text);
    }

    @Override
    public Optional<Command> getInputCommand(String prompt) {
        System.out.print(MessageFormat.format("{0}", prompt));
        String input = scanner.nextLine();
        Optional<Command> command = Command.valueOfCommand(input);
        return command;
    }

    @Override
    public Voucher inputVoucherInfo() {
        while (true) {
            System.out.println("=== Create Voucher ===");
            System.out.println("1. FixedAmountVoucher");
            System.out.println("2. PercentDiscountVoucher");
            System.out.println("Select voucher type [1, 2]: ");
            try {
                int select = Integer.parseInt(scanner.nextLine());
                return switch (select) {
                    case 1 -> {
                        System.out.println("Enter fixed amount: ");
                        String fixedAmount = scanner.nextLine();
                        yield new FixedAmountVoucher(fixedAmount);
                    }
                    case 2 -> {
                        System.out.println("Enter discount percent: ");
                        String percent = scanner.nextLine();
                        yield new PercentDiscountVoucher(percent);
                    }
                    default -> {
                        throw new InputMismatchException();
                    }
                };
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid voucher type. Select again!!");
            }
        }
    }

    @Override
    public void printCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println();
    }

    @Override
    public void printAllVouchers(List<Voucher> vouchers) {
        System.out.println("=== List of Vouchers ===");
        vouchers.forEach(this::printVoucherInfo);
    }

    private void printVoucherInfo(Voucher voucher) {
        System.out.println(voucher.getVoucherInfo());
    }
}
