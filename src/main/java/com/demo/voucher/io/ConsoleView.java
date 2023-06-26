package com.demo.voucher.io;

import com.demo.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleView implements Input, Output {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String VOUCHER_PROGRAM_START_OUTPUT = "=== Voucher Program ===";

    @Override
    public String getMenu() {
        return scanner.nextLine();
    }

    @Override
    public String getVoucherType() {
        return null;
    }

    @Override
    public String getAmount() {
        return null;
    }

    @Override
    public void showMenu() {
        System.out.println(VOUCHER_PROGRAM_START_OUTPUT);
        Arrays.stream(CommandType.values())
                .forEach(c -> System.out.println(c.toString()));
    }

    @Override
    public void showVoucherType() {

    }

    @Override
    public void inputError(String errorResponse) {

    }

    @Override
    public void showAllVoucher(Map<UUID, Voucher> voucherHistory) {

    }
}
