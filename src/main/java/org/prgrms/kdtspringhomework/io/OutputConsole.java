package org.prgrms.kdtspringhomework.io;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;

import java.util.List;

public class OutputConsole implements Output {
    //프로그램 시작 시 출력 메시지
    @Override
    public void start() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    //블랙리스트 시작시 출력 메시지
    @Override
    public void startBlackList() {
        System.out.println("=== Black List Program ===");
    }

    //동작 입력 메시지
    @Override
    public void inputCommandTypeMessage() {
        System.out.print("Select 'create' or 'list' or 'exit': ");
    }

    //voucher 타입 메시지
    @Override
    public void inputVoucherTypeMessage() {
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
        System.out.print("The program has ended.");
    }

    //커맨드 입력 오류
    @Override
    public void invalidCommandType() {
        System.out.println("Write valid command.");
    }

    //타입 입력 오류
    @Override
    public void invalidVoucherType() {
        System.out.println("Write valid type");
    }

    //금액 입력 오류
    @Override
    public void invalidAmount() {
        System.out.println("Write valid amount");
    }

    //list 출력
    @Override
    public void printVouchers(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println(voucher.toString());
        }
    }


}
