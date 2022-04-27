package com.waterfogsw.voucher.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public Command inputCommand() {
        display(Messages.COMMAND_INPUT);
        String commandInput = sc.nextLine();
        return Command.from(commandInput);
    }

    @Override
    public String inputType() {
        display(Messages.VOUCHER_SELECT);
        String input = sc.nextLine();
        final int typeNum = Integer.parseInt(input);
        return VoucherType.from(typeNum).name();
    }

    @Override
    public String inputValue() {
        display(Messages.INPUT_VALUE);
        return sc.nextLine();
    }

    @Override
    public void display(String message) {
        System.out.print("\n"+message);
    }
}
