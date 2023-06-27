package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.request.VoucherCreateRequest;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

@Component
public class TextIoConsole implements Console {
    private final TextIO textIO;

    public TextIoConsole(TextIO textIO) {
        this.textIO = textIO;
    }

    @Override
    public ConsoleCommandType inputInitialCommand() {
        String command = textIO.newStringInputReader()
                .read();

        return ConsoleCommandType.getValue(command);
    }

    @Override
    public void printCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println("=== Voucher Program ===");
        printCommand(ConsoleCommandType.EXIT, "to exit the program.");
        printCommand(ConsoleCommandType.CREATE, "to create a new voucher.");
        printCommand(ConsoleCommandType.LIST, "to list all vouchers.");
        printCommand(ConsoleCommandType.HELP, "to list command set.");
    }

    private void printCommand(ConsoleCommandType commandType, String behavior) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print("Type");
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" " + commandType.getInput() + " ")
        );
        textTerminal.println(behavior);
    }

    @Override
    public VoucherCreateRequest inputVoucherCreateInfo() {
        VoucherType voucherType = inputVoucherType();
        long amount = inputDiscountAmount();
        voucherType.validateAmount(amount);

        return new VoucherCreateRequest(voucherType, amount);
    }

    private VoucherType inputVoucherType() {
        String rawVoucherType = textIO.newStringInputReader()
                .read("1. [fixed | percent]");

        return VoucherType.getValue(rawVoucherType);
    }

    private long inputDiscountAmount() {
        return textIO.newLongInputReader()
                .read("2. [amount]");
    }

    @Override
    public void print(String result) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(result);
    }

    @Override
    public void exit() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println("Bye Bye.");
    }

}
