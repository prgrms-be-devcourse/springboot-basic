package com.programmers.io;

import com.programmers.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final String MENU_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.";
    private static final String VOUCHER_TYPE_MESSAGE = "\n=== Voucher Type ===\n" +
            "Select voucher. (Type voucher name or number.)\n" +
            "1. Fixed Amount Voucher\n" +
            "2. Percent Discount Voucher";
    private static final String DISCOUNT_VALUE_MESSAGE = "\n=== Type discount amount or rate ===";
    private static final String VOUCHER_NAME_MESSAGE = "\n=== Type voucher name ===";
    private static final String VOUCHER_CREATED_MESSAGE = "--- Voucher Created !! ---\n";
    private static final String VOUCHER_LIST_TITLE_MESSAGE = "\n=== Voucher List ===";

    @Override
    public void printMenu() {
        System.out.println(MENU_MESSAGE);
    }

    @Override
    public String readInput() {
        return new Scanner(System.in).nextLine();
    }

    @Override
    public void printVoucherType() {
        System.out.println(VOUCHER_TYPE_MESSAGE);
    }

    @Override
    public String readVoucherName() {
        String voucherType = readInput();

        return reformatVoucherName(voucherType);
    }

    public String reformatVoucherName(String input) {
        return input.trim().replace(" ", "").toLowerCase();
    }

    @Override
    public void printDiscountValueInput() {
        System.out.println(DISCOUNT_VALUE_MESSAGE);
    }

    @Override
    public void printVoucherNameInput() {
        System.out.println(VOUCHER_NAME_MESSAGE);
    }

    public void printVoucherCreated() {
        System.out.println(VOUCHER_CREATED_MESSAGE);
    }

    @Override
    public void printVoucherListTitle() {
        System.out.println(VOUCHER_LIST_TITLE_MESSAGE);
    }

    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);

        System.out.println();
    }
}
