package com.prgms.voucher.voucherproject.io;

import java.util.Scanner;

public class Console implements Input, Output{

    private static final Scanner sc = new Scanner(System.in);

    /** Output **/
    @Override
    public void printMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
    }

    @Override
    public void printErrorMsg() {
        System.out.println("잘못된 명령어입니다.");
    }

    @Override
    public void printNoVoucher() {
        System.out.println("존재하는 바우처가 없습니다.");
    }

    /** Input **/
    @Override
    public String inputCommand() {
        return sc.nextLine();
    }
}
