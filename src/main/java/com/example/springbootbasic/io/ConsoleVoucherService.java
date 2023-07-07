package com.example.springbootbasic.io;

import com.example.springbootbasic.voucher.FixedAmountVoucher;
import com.example.springbootbasic.voucher.PercentDiscountVoucher;
import com.example.springbootbasic.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleVoucherService implements Input, Output {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleVoucherService.class);
    Scanner scanner = new Scanner(System.in);

    public static void print(String text) {
        System.out.println(text);
    }

    @Override
    public Optional<Command> getInputCommand(String prompt) {
        System.out.print(MessageFormat.format("{0}", prompt));
        String input = scanner.nextLine();
        return Command.valueOfCommand(input);
    }

    @Override
    public Voucher inputVoucherInfo() {
        while (true) {
            printAvailableVoucherList();
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
                    default -> throw new InputMismatchException("Wrong type voucher input");
                };
            } catch (Exception e) {
                System.out.println("Invalid input. Select again!!");
                logger.error("Exception type: {}, message: {}", e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    private void printAvailableVoucherList() {
        System.out.println("=== Create Voucher ===");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.println("Select voucher type [1, 2]: ");
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
        vouchers.forEach(Voucher::printInfo);
    }
}
