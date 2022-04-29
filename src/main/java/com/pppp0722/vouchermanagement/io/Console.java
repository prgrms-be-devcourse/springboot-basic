package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.engine.command.CommandType;
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
    public void printInputError() {
        System.out.println("Wrong input! Try again.");
    }

    @Override
    public void printEmptyResult() {
        System.out.println("Empty.");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            printEmptyResult();
        } else {
            for (Voucher voucher : voucherList) {
                System.out.println(voucher);
            }
        }
    }

    @Override
    public void printMemberList(List<Member> memberList) {
        if (memberList.isEmpty()) {
            printEmptyResult();
        } else {
            for (Member member : memberList) {
                System.out.println(member);
            }
        }
    }

    @Override
    public void printMember(Member member) {
        System.out.println(member);
    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    @Override
    public String getCommand() {
        System.out.print("Input : ");
        String command = scanner.nextLine();
        System.out.println();
        return command;
    }

    @Override
    public CommandType inputCommandType() throws IllegalArgumentException {
        return CommandType.valueOf(getCommand().toUpperCase());
    }

    @Override
    public EntityType inputEntityType() throws IllegalArgumentException {
        System.out.println("member\nvoucher");
        return EntityType.valueOf(getCommand().toUpperCase());
    }

    @Override
    public VoucherType inputVoucherType() throws IllegalArgumentException {
        System.out.println("fixed_amount\npercent_discount");
        return VoucherType.valueOf(getCommand().toUpperCase());
    }

    @Override
    public UUID inputMemberId() throws IllegalArgumentException {
        System.out.println("Type member id.");
        return UUID.fromString(getCommand());
    }

    @Override
    public UUID inputVoucherId() throws IllegalArgumentException {
        System.out.println("Type voucher id.");
        return UUID.fromString(getCommand());
    }

    @Override
    public String inputMemberReadType() {
        System.out.println("1. all\n2. by member id");
        return getCommand();
    }

    @Override
    public String inputVoucherReadType() {
        System.out.println("1. all\n2. by voucher id\n3. by member id");
        return getCommand();
    }

    @Override
    public String inputMemberName() {
        System.out.println("Type name.");
        return getCommand();
    }

    @Override
    public long inputVoucherAmount() throws IllegalArgumentException {
        System.out.println("Type discount amount.");
        return Long.parseLong(getCommand());
    }
}
