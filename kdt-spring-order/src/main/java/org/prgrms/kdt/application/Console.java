package org.prgrms.kdt.application;

import org.prgrms.kdt.application.voucher.io.Input;
import org.prgrms.kdt.application.voucher.io.Output;
import org.prgrms.kdt.application.voucher.type.CommandType;
import org.prgrms.kdt.application.voucher.type.VoucherType;
import org.prgrms.kdt.domain.voucher.domain.Voucher;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner sc= new Scanner(System.in);

    private int input(String inputMessage) {
        System.out.print(inputMessage);
        String inputString = sc.nextLine();
        int choice = -1;
        try {
            choice = Integer.parseInt(inputString.replaceAll(" ", ""));
        } catch (NumberFormatException ignored) { }
        return choice;
    }


    @Override
    public Optional<CommandType> inputCommand() {
        return CommandType.fromOrdinal(input("명령어 번호 입력 : "));
    }

    @Override
    public Optional<VoucherType> inputVoucherType() {
        return VoucherType.fromOrdinal(input("바우처 종류 번호 입력 : "));
    }

    @Override
    public OptionalLong inputVoucherTypeValue(String value) {
        System.out.print(value + " 입력 : ");
        String inputString = sc.nextLine();
        OptionalLong optionalLong;
        try {
            optionalLong = OptionalLong.of(Long.parseLong(inputString.replaceAll(" ", "")));
        } catch (NumberFormatException exception) {
            optionalLong = OptionalLong.empty();
        }
        return optionalLong;
    }

    @Override
    public void printProgramName() {
        System.out.println("\n======== 바우처 프로그램 ========");
    }

    @Override
    public void printCommandList() {
        String commandList = "\n원하는 명령어 번호를 입력해주세요.\n" +
                CommandType.getListOfEnum();
        System.out.println(commandList);
    }

    @Override
    public void printInputError() {
        System.out.println("*** 잘못된 입력입니다 ***");
    }

    @Override
    public void printExit() {
        System.out.println("\n======== 프로그램 종료 ========");
    }

    @Override
    public void printVoucherTypeList() {
        String voucherTypeList = "\n원하는 바우처 종류 번호를 입력해주세요.\n" +
                VoucherType.getListOfEnum();
        System.out.println(voucherTypeList);
    }

    @Override
    public void printVoucherCreateResult(boolean isCreated) {
        if(isCreated)
            System.out.println("*** 바우처가 생성되었습니다 ***");
        else
            System.out.println("*** 이미 존재하는 바우처입니다 ***");
    }

    @Override
    public void printVoucherList(Collection<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void printNoneVoucherList() {
        System.out.println("*** 바우처가 없습니다 ***");
    }
}
