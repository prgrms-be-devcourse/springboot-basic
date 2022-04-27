package org.prgrms.kdt.shop.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printMenu( ) {
        System.out.println("=== Voucher Program ===\nType exit to exit the program\nType create to create a new voucher.\nType list to list all vouchers.\n");
    }

    @Override
    public void printInputError( ) {
        System.out.println("input error");
    }

    @Override
    public void printVoucherSelector( ) {
        System.out.println("1. FixedAmountVoucher 2. PercentDiscountVoucher");
        System.out.print("입력 : ");
    }

    @Override
    public void printDiscountInput( ) {
        System.out.print("할인 입력 : ");
    }

    @Override
    public void printMenuSelector( ) {
        System.out.print("메뉴 입력 : ");
    }

    @Override
    public String getInput( ) {
        return scanner.nextLine().trim();
    }
}
