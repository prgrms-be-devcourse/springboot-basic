package com.programmers.assignment.voucher.engine.io;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);


    @Override
    public String inputCommand(String commandMessage) {
        System.out.println(commandMessage);
        return scanner.nextLine().trim().toUpperCase();
    }

    @Override
    public String selectVoucher(String voucher) {
        System.out.println(voucher);
        return scanner.nextLine().trim().toUpperCase();
    }

    @Override
    public String inputVoucherInfo(String voucherInfo) {
        System.out.println(voucherInfo);
        return scanner.nextLine();
    }
}
