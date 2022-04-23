package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.engine.command.EntityType;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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
        System.out.println("create\nread\nupdate\ndelete\nexit");
    }

    @Override
    public void printSuccess() {
        System.out.println("Success.");
    }

    @Override
    public void printFailure() {
        System.out.println("Failed!");
    }

    @Override
    public void printEmpty() {
        System.out.println("Empty.");
    }

    @Override
    public void printInputError() {
        System.out.println("Wrong input! Try again.");
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
    public void printMemberList(List<Member> memberList) {
        for (Member member : memberList) {
            System.out.println("member id: " + member.getMemberId() +
                ", name : " + member.getName());
        }
    }

    @Override
    public String getCommand() {
        System.out.print("Input : ");
        String command = scanner.nextLine().toLowerCase();
        System.out.println();
        return command;
    }

    @Override
    public EntityType inputEntityType(String question) {
        System.out.println(question);
        return EntityType.getEntityType(getCommand());
    }

    @Override
    public String inputCount() {
        System.out.println("all\none");
        return getCommand();
    }

    @Override
    public String inputMemberId() {
        System.out.println("Type member id.");
        return getCommand();
    }

    @Override
    public String inputMemberName() {
        System.out.println("Type name.");
        return getCommand();
    }

    @Override
    public String inputVoucherId() {
        System.out.println("Type voucher id.");
        return getCommand();
    }

    @Override
    public VoucherType inputVoucherType() {
        System.out.println("Type fixed to create a new fixed amount voucher.\n" +
            "Type percent to create a new percent discount voucher.");
        return VoucherType.getVoucherType(getCommand());
    }

    @Override
    public long inputVoucherAmount() {
        System.out.println("Type discount amount.");
        return Long.parseLong(getCommand());
    }
}
