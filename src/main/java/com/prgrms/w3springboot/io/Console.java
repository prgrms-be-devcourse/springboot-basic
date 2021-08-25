package com.prgrms.w3springboot.io;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.UUID;

public class Console implements Input, Output {
    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String input() {
        return scanner.next();
    }

    @Override
    public void printInit() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===\n");
        sb.append("Type exit to exit the program.\n");
        sb.append("Type create to create a new voucher.\n");
        sb.append("Type list to list all vouchers.");
        System.out.println(sb);
    }

    @Override
    public void printTypeChoice() {
        System.out.print("Which type of voucher do you want to make? [fixed | percent] > ");
    }

    @Override
    public void printDiscountAmountChoice() {
        System.out.print("How much discount do you want to get? > ");
    }

    public void printVoucher(UUID createdVoucherUuid) {
        System.out.println(MessageFormat.format("Voucher ID [{0}] is just created!", createdVoucherUuid));
    }

    @Override
    public void printExit() {
        System.out.println("Have a Nice Day! :)");
    }
}
