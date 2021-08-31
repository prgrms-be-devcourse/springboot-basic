package org.prgrms.kdtspringhomework.io;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner sc = new Scanner(System.in);

    //사용자 입력값 받기
    @Override
    public String input() {
        return sc.nextLine();
    }

    //프로그램 시작 시 출력 메시지
    @Override
    public void start() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    //동작 입력 메시지
    @Override
    public void inputTypeMessage() {
        System.out.print("Select 'create' or 'list' or 'exit': ");
    }

    //voucher 타입 메시지
    @Override
    public void voucherTypeMessage() {
        System.out.print("Select 'fixed' or 'percent': ");
    }

    //금액 입력 메시지
    @Override
    public void inputAmountMessage() {
        System.out.print("Input Amount: ");
    }

    //성공 시 출력 메시지
    @Override
    public void success() {
        System.out.println("Create voucher successfully.");
    }

    //프로그램 종료 시 출력 메시지
    @Override
    public void exit() {
        System.out.println("The program has ended.");
        System.exit(0);
    }

    //오류 발생시 출력 메시지
    @Override
    public void inputError() {
        System.out.println("You write invalid input.");
    }

}
