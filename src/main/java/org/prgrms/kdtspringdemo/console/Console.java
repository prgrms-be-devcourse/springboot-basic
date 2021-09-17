package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Console implements Input, Output {
    private final static Logger logger = LoggerFactory.getLogger(Console.class);
    private final Scanner input;

    public Console() {
        this.input = new Scanner(System.in);
    }

    @Override
    public void printStartAppInfo() {
        System.out.println("=== Voucher Program ===");
    }

    @Override
    public void printCommandList() {
        System.out.println("""
                Type exit to exit the program.
                Type create to create a new customer/voucher.
                Type list to list customers/vouchers.
                Type blacks to list all blacklist customers
                Type delete customer/voucher.
                Type find by id customer/voucher""");
    }

    @Override
    public void printCommandError(String command) {
        String message = """
                [ERROR]Invalid command type 
                Your input : {0}
                ===========""";
        System.out.println(MessageFormat.format(message, command));
    }

    @Override
    public void printCreateSelect() {
        System.out.print("""
                Select Create Object type.
                1. Customer
                2. Voucher
                Input select number(1 or 2):""");
    }

    @Override
    public void printCreateCustomerByTypes() {
        System.out.print("""
                <name> <email> <customerType>
                customerType(NORMAL, BLACK)
                data is string value
                input customer info:""");
    }

    @Override
    public void printCreateVoucherByTypes() {
        System.out.print("""
                <customerId> <vouhcerType> <data>
                voucherType
                \tP : PercentDiscountVoucher(1~100)
                \tF : FixedAmountVoucher(1~100000)
                data is long value
                ex) <id> P 10
                input voucher info:""");
    }

    @Override
    public void printListSelect() {
        System.out.print("""
                Select List Object type.
                1. Customer
                2. Voucher
                Input select number(1 or 2):""");
    }

    @Override
    public void printObject(Object obj) {
        System.out.println(obj.toString());
    }

    @Override
    public void printDeleteSelect() {
        System.out.print("""
                Select Delete Object type.
                1. Customer
                2. Voucher
                Input select number(1 or 2):""");
    }

    @Override
    public void printDeleteCustomer() {
        System.out.print("""
                <customerId>
                select to delete customerId:""");
    }

    @Override
    public void printDeleteVoucher() {
        System.out.print("""
                <vouhcerId>
                select to delete voucherId:""");
    }

    @Override
    public void printFindSelect() {
        System.out.print("""
                Select Find Object type.
                1. Customer
                2. Voucher
                Input select number(1 or 2):""");
    }

    @Override
    public void printFindCustomer() {
        System.out.print("""
                <vouhcerId>
                find customer data by voucherId:""");
    }

    @Override
    public void printFindVouchers() {
        System.out.print("""
                <customerId>
                find voucher datas by customerId:""");
    }

    @Override
    public <T> void printList(List<T> list) {
        list.forEach(System.out::println);
    }

    @Override
    public CommandType getInputCommand() {
        printCommandList();
        System.out.print("input command:");
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
