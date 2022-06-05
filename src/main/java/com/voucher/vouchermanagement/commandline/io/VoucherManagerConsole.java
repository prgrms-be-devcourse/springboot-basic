package com.voucher.vouchermanagement.commandline.io;

import com.voucher.vouchermanagement.commandline.command.CommandType;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class VoucherManagerConsole implements VoucherManagerInput, VoucherManagerOutput {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.print(prompt);

        return scanner.nextLine();
    }

    @Override
    public void println(String string) {
        System.out.println(string);
    }

    @Override
    public void printMenu() {
        System.out.println("=== Voucher Program ===");

        Arrays.stream(CommandType.values())
                .filter(commandType -> commandType != CommandType.NONE)
                .map(CommandType::getCommandDescription)
                .forEach(System.out::println);
    }

    @Override
    public void printVoucherType() {
        System.out.println("=== Voucher Type ===");

        Arrays.stream(VoucherType.values())
                .forEach(System.out::println);
    }
}
