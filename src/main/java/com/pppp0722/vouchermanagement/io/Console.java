package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printLogo() {
        System.out.println("=== Voucher Program ===");
    }

    @Override
    public void printMenu() {
        System.out.println("Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n" +
                "Type black to print a blacklist.\n" +
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
    public void printVoucherTypeInputRequest() {
        System.out.println("Type fixed to create a new fixed amount voucher.\n" +
                "Type percent to create a new percent discount voucher.");
    }

    @Override
    public void printAmountInputRequest() {
        System.out.println("Type discount amount.");
    }

    @Override
    public void printVoucherEmpty() {
        System.out.println("Voucher list is empty.");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        for(Voucher voucher : voucherList) {
            System.out.println("voucher id : " + voucher.getVoucherId() +
                    ", voucher type: " + voucher.getVoucherType() +
                    ", amount : " + voucher.getAmount());
        }
    }

    @Override
    public void printBlackListEmpty() {
        System.out.println("Blacklist is empty.");
    }

    @Override
    public void printBlackList(List<Member> blackList) {
        for(Member member : blackList) {
            System.out.println("member id: " + member.getMemberId() +
                    ", name : " + member.getName());
        }
    }

    @Override
    public String getCommand(String question) {
        System.out.print(question);
        String command = scanner.nextLine().toLowerCase();
        printEmpty();

        return command;
    }
}
