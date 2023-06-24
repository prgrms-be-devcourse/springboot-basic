package kr.co.programmers.springbootbasic.io;


import kr.co.programmers.springbootbasic.util.VoucherUtils;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleIO implements Input, Output {
    private final Scanner scanner;

    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public MenuCommand readMenuCommand() {
        String input = scanner.nextLine();

        return MenuCommand.resolve(input);
    }

    @Override
    public VoucherType readVoucherType() {
        String input = scanner.nextLine();

        return VoucherType.resolve(input);
    }

    @Override
    public long readAmount() {
        String input = scanner.nextLine();

        return VoucherUtils.parseStringToLong(input);
    }

    @Override
    public void printProgramMenu() {
        System.out.print(ConsoleMessage.VOUCHER_PROGRAM_MENU);
    }

    @Override
    public void printCreationMenu() {
        System.out.print(ConsoleMessage.VOUCHER_CREATION_MENU);
    }

    @Override
    public void printAmountEnterMessage(VoucherType voucherType) {
        switch (voucherType) {
            case PERCENT_AMOUNT -> System.out.print(ConsoleMessage.PERCENT_AMOUNT_ENTER_MESSAGE);
            case FIXED_AMOUNT -> System.out.print(ConsoleMessage.FIXED_AMOUNT_ENTER_MESSAGE);
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.print(message);
    }

    @Override
    public void printExit() {
        System.out.print(ConsoleMessage.EXIT_MESSAGE);
    }
}
