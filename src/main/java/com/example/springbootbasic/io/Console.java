package com.example.springbootbasic.io;

import com.example.springbootbasic.voucher.FixedAmountVoucher;
import com.example.springbootbasic.voucher.PercentDiscountVoucher;
import com.example.springbootbasic.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Console implements Input, Output {
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
    public void printCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println();
    }

    @Override
    public void printAllVouchers(List<Voucher> vouchers) {
        vouchers.forEach(this::printVoucherInfo);
    }

    private void printVoucherInfo(Voucher voucher) {
        System.out.print(MessageFormat.format("Voucher Type: {0}", voucher.getClass().getName()));
        if (voucher.getClass().equals(FixedAmountVoucher.class)) {
            System.out.println(", fixedAmount: " + ((FixedAmountVoucher) voucher).getFixedAmount() + "$");
            return;
        }
        if (voucher.getClass().equals(PercentDiscountVoucher.class)) {
            System.out.println(", percent: " + ((PercentDiscountVoucher) voucher).getPercent() + "%");
        }
    }
}
