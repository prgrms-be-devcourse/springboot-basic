package com.example.springbootbasic;

import com.example.springbootbasic.io.Command;
import com.example.springbootbasic.io.Input;
import com.example.springbootbasic.io.Output;
import com.example.springbootbasic.voucher.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Console implements Input, Output {
    Scanner scanner = new Scanner(System.in);

    @Override
    public Optional<Command> getInputCommand(String prompt) {
        System.out.print(MessageFormat.format("{0} > ", prompt));
        return Command.valueOfCommand(scanner.nextLine());
    }

    @Override
    public void printCommand() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit 계the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println();
    }

    @Override
    public void listAllVoucher(List<Voucher> vouchers) {

    }
}
