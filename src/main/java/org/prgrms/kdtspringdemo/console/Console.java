package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.io.*;
import org.prgrms.kdtspringdemo.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Scanner;

public class Console implements Input, Output {
    private final static Logger logger = LoggerFactory.getLogger(Console.class);
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
                Type list to list all vouchers.
                Type customers to list all customers
                Type blacks to list all blacklist customers""");
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
    public CommandType getInputCommand() {
        ;
        String command = input.nextLine();
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error(MessageFormat.format("Invalid command type. Your input -> {0}", command));
            printCommandError(command);
            return CommandType.ERROR;
        }
    }

    @Override
    public String getCreateLine() {
        return input.nextLine();
    }
}
