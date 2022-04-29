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
    public void printFailure() {
        System.out.println("Failed!");
    }

    @Override
    public String getCommand() {
        System.out.print("Input : ");
        String command = scanner.nextLine();
        System.out.println();
        return command;
    }

    @Override
    public CommandType inputCommandType() {
        try {
            return CommandType.valueOf(getCommand().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid command type!");
        }
    }

    @Override
    public EntityType inputEntityType() {
        System.out.println("member\nvoucher");
        try {
            return EntityType.valueOf(getCommand().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid entity type!");
        }
    }

    @Override
    public VoucherType inputVoucherType() {
        System.out.println("fixed_amount\npercent_discount");
        try {
            return VoucherType.valueOf(getCommand().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid voucher type!");
        }
    }

    @Override
    public UUID inputMemberId() {
        System.out.println("Type member id.");
        try {
            return UUID.fromString(getCommand());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Check UUID format.");
        }
    }

    @Override
    public UUID inputVoucherId() {
        System.out.println("Type voucher id.");
        try {
            return UUID.fromString(getCommand());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Check UUID format.");
        }
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
    public long inputVoucherAmount() {
        System.out.println("Type discount amount.");
        try {
            return Long.parseLong(getCommand());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not a number.");
        }
    }
}
