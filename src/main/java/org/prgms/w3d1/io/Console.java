package org.prgms.w3d1.io;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int inputInt(String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    @Override
    public void inputError() {
        System.out.println("입력이 잘못되었습니다.");
    }

    @Override
    public void printFixedMenu(){
        System.out.println("=== Create FixedAmountVoucher ===");
    }

    @Override
    public void printPercentMenu(){
        System.out.println("=== Create PercentDiscountVoucher ===");
    }

    @Override
    public void printBlackListCreateMenu() {
        System.out.println("=== input name ===");
    }
}
