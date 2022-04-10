package com.prgrms.vouchermanagement.io;

import com.prgrms.vouchermanagement.StringUtils;
import com.prgrms.vouchermanagement.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output{

    private final static Logger log = LoggerFactory.getLogger(Console.class);

    private final Scanner sc = new Scanner(System.in);

    @Override
    public int inputVoucherType() throws InputMismatchException{
        System.out.println();
        System.out.println("Select voucher type to add");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("input type: ");
        String input = sc.nextLine();

        if (!StringUtils.isNumber(input)) {
            throw new InputMismatchException();
        }

        return Integer.parseInt(input);
    }

    @Override
    public int inputDiscount() throws InputMismatchException {
        System.out.print("input discount: ");
        String input = sc.nextLine();

        if (!StringUtils.isNumber(input)) {
            throw new InputMismatchException();
        }

        return Integer.parseInt(input);
    }

    @Override
    public String inputCommand() {
        System.out.print("input: ");
        return sc.nextLine();
    }

    @Override
    public void printMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void printList(List<Voucher> vouchers) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=== Voucher List ===").append("\n");
        vouchers.forEach(v -> sb.append(v).append("\n"));
        System.out.println(sb);
    }

    @Override
    public void printMessage(String errorMessage) {
        System.out.println();
        System.out.println(errorMessage);
        System.out.println();
    }
}
