package com.programmers.io;

import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final String MENU_MESSAGE = "=== Voucher Program ===\n" +
            "Type 'exit' or '1' to exit the program.\n" +
            "Type 'create' or '2' to create a new voucher.\n" +
            "Type 'list' or '3' to list all vouchers.\n" +
            "Type 'black' or '4' to check the blacklist.";
    private static final String VOUCHER_TYPE_MESSAGE = "\n=== Voucher Type ===\n" +
            "Type voucher name or number to create.\n" +
            "1. Fixed Amount Voucher\n" +
            "2. Percent Discount Voucher";
    private static final String DISCOUNT_VALUE_MESSAGE = "\n=== Type discount amount or rate ===";
    private static final String VOUCHER_NAME_MESSAGE = "\n=== Type voucher name ===";
    private static final String VOUCHER_CREATED_MESSAGE = "--- Voucher Created !! ---\n";
    private static final String VOUCHER_LIST_TITLE_MESSAGE = "\n=== Voucher List ===";
    private static final String BLACKLIST_MESSAGE = "\n=== Blacklist ===";

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

    public void printVouchers(VouchersResponseDto vouchersResponseDto) {
        List<Voucher> vouchers = vouchersResponseDto.vouchers();
        vouchers.forEach(System.out::println);
        System.out.println();
    }

    public void printBlacklistTitle() {
        System.out.println(BLACKLIST_MESSAGE);
    }

    public void printBlacklist(List<String> blacklist) {
        blacklist.forEach(System.out::println);

        System.out.println();
    }
}
