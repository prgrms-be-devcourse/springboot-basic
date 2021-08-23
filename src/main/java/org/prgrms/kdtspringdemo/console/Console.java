package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.io.*;

import java.text.MessageFormat;
import java.util.Scanner;

public class Console implements Input, Output {
    private Scanner input;

    public Console() {
        this.input = new Scanner(System.in);
    }

    @Override
    public void printStartAppInfo() {
        System.out.println("=== Voucher Program ===");
        printCommandList();
    }

    @Override
    public void printCommandList() {
        System.out.println("""
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""");
    }

    @Override
    public void printCommandError(String command) {
        System.out.println(MessageFormat.format("[ERROR]Invalid command type \nYour input : {0}", command));
        printCommandList();
        System.out.println("=================");
    }

    @Override
    public void printCreateTypes() {
        System.out.println("""
                input voucher info
                <vouhcerType> <data>
                voucherType
                \tP : FixedAmountVoucher
                \tF : PercentDiscountVoucher
                data is long value
                ex) P 10""");
    }

    @Override
    public String getInputCommand() {
        return input.nextLine();
    }
}
