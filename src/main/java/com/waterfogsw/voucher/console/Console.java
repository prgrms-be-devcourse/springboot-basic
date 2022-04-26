package com.waterfogsw.voucher.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.stream.Stream;

import static com.waterfogsw.voucher.console.Messages.INVALID_TYPE;

@Component
public class Console implements Input, Output {

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public Command inputCommand() {
        System.out.print(Messages.COMMAND_INPUT);
        String commandInput = sc.nextLine();
        return Command.getCommand(commandInput);
    }

    @Override
    public String inputType() {
        System.out.print(Messages.VOUCHER_SELECT);
        String input = sc.nextLine();
        try {
            int typeNum = Integer.parseInt(input);
            return VoucherType.getVoucherType(typeNum).name();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_TYPE);
        }
    }

    @Override
    public String inputValue() {
        System.out.print(Messages.INPUT_VALUE);
        return sc.nextLine();
    }

    @Override
    public void printCommandList() {
        Stream.of(CommandList.values())
                .map(CommandList::getMessage)
                .forEach(System.out::println);
    }

    @Override
    public void printVoucherTypes() {
        System.out.println();
        Stream.of(VoucherType.values())
                .forEach((v) -> System.out.println(v.getNum() + " : " + v));
    }

    @Override
    public void printCreatedVoucher(String voucherInfo) {
        System.out.println();
        System.out.println(voucherInfo);
    }

    @Override
    public void printAllVoucher(String voucherList) {
        System.out.println();
        System.out.println(voucherList);
    }

    @Override
    public void printExitMessage() {
        System.out.println(Messages.EXIT_PROGRAM);
    }

    @Override
    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
