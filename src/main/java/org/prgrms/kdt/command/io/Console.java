package org.prgrms.kdt.command.io;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void printOnStart() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    @Override
    public void printOnExit() {
        System.out.println("Exit Voucher Program.");
    }

    @Override
    public void printVoucherList(List<Voucher> vouchers) {
        System.out.println("Print All Voucher List.");
        vouchers.forEach((voucher) -> System.out.println(voucher.toString()));
    }

    @Override
    public void printRequestVoucherType() {
        System.out.println("Input Voucher Type(PERCENT/FIXED) : ");
    }

    @Override
    public void printRequestVoucherValue(VoucherType type) {
        System.out.println(MessageFormat.format("Input discount {0} : ", type.toString()));
    }

    @Override
    public void printInputError() {
        System.out.println("Invalid Input Error. Please type correct command.");
    }

    @Override
    public String inputString(final String inputPrompt) {
        System.out.print(inputPrompt);
        return this.scanner.nextLine();
    }
}
