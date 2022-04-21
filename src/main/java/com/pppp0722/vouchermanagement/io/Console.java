package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.entity.member.Member;
import com.pppp0722.vouchermanagement.entity.voucher.Voucher;

import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    private Console() {
    }

    private static class ConsoleSingletonHelper {

        private static final Console INSTANCE = new Console();
    }

    public static Console getInstance() {
        return ConsoleSingletonHelper.INSTANCE;
    }

    @Override
    public void printLogo() {
        System.out.println("=== Voucher Program ===");
    }

    @Override
    public void printMenu() {
        System.out.println("Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n" +
            "Type exit to exit the program.");
    }

    @Override
    public void printEmpty() {
        System.out.println();
    }

    @Override
    public void printInputError() {
        System.out.println("Wrong input! Try again.");
    }

    @Override
    public void printError(String error) {
        System.out.println(error + " error happened!");
    }

    @Override
    public void printEntityTypeInputRequest1() {
        System.out.println("member\nvoucher");
    }

    @Override
    public void printEntityTypeInputRequest2() {
        System.out.println("member\nvoucher\nwallet");
    }

    @Override
    public void printVoucherTypeInputRequest() {
        System.out.println("Type fixed to create a new fixed amount voucher.\n" +
            "Type percent to create a new percent discount voucher.");
    }

    @Override
    public void printNameInputRequest() {
        System.out.println("Type name.");
    }

    @Override
    public void printAmountInputRequest() {
        System.out.println("Type discount amount.");
    }

    @Override
    public void printMemberIdInputRequest() {
        System.out.println("Type member id.");
    }

    @Override
    public void printVoucherEmpty() {
        System.out.println("Voucher list is empty.");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println("voucher id : " + voucher.getVoucherId() +
                ", voucher type: " + voucher.getType() +
                ", amount : " + voucher.getAmount() +
                ", member id : " + voucher.getMemberId());
        }
    }

    @Override
    public void printMemberListEmpty() {
        System.out.println("Member list is empty.");
    }

    @Override
    public void printMemberList(List<Member> memberList) {
        for (Member member : memberList) {
            System.out.println("member id: " + member.getMemberId() +
                ", name : " + member.getName());
        }
    }

    @Override
    public String getCommand(String question) {
        System.out.print(question);
        String command = scanner.nextLine().toLowerCase();
        System.out.println();;

        return command;
    }
}
