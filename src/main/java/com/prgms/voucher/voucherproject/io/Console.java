package com.prgms.voucher.voucherproject.io;


import java.util.Scanner;

public class Console implements Input, Output{

    public Console(){}

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public void printErrorMsg() {
        System.out.println("잘못된 명령어입니다.");
    }

    @Override
    public void printNoVoucher() {
        System.out.println("존재하는 바우처가 없습니다.");
    }

    @Override
    public void printMsg(String msg, boolean lnCheck) {
        if(lnCheck){
            System.out.println(msg);
        }
        else{
            System.out.print(msg);
        }
    }

    @Override
    public String inputCommand() {
        return sc.nextLine();
    }

    @Override
    public int inputSelectedVoucherType() {
        return sc.nextInt();
    }

    @Override
    public long inputDiscountAmount() {
        return sc.nextLong();
    }

    @Override
    public String bufferDeleted() {
        return sc.nextLine();
    }
}
