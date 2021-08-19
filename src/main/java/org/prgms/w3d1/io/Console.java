package org.prgms.w3d1.io;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);


    @Override
    public String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public void inputError() {
        System.out.println("입력이 잘못되었습니다.");
    }

    @Override
    public void printMenual(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type blacklist to move blacklist menu.");

    }

    @Override
    public void printCreateMenu(){
        System.out.println("=== Create Voucher ===");
        System.out.println("type number for a voucher what you want");
        System.out.println("1 : FixedAmountVoucher");
        System.out.println("2 : PercentDiscountVoucher");
    }

    @Override
    public void printFixedMenu(){
        System.out.println("=== Create FixedAmountVoucher ===");
        System.out.println("type discount amount");
    }

    @Override
    public void printPercentMenu(){
        System.out.println("=== Create PercentDiscountVoucher ===");
        System.out.println("type discount percent");
    }

    @Override
    public void printBlackListMenu() {
        System.out.println("=== Blacklist Menu ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new Blacklist.");
        System.out.println("Type list to list all Blacklists.");

    }

    @Override
    public void printBlackListCreateMenu() {
        System.out.println("=== input name ===");
    }
}
