package com.programmers.voucher.view;

import com.programmers.voucher.view.command.Command;
import com.programmers.voucher.view.command.CustomerCommand;
import com.programmers.voucher.view.command.VoucherCommand;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsoleInput implements Input {
    private static final TextIO TEXT_IO = TextIoFactory.getTextIO();

    @Override
    public Command readCommand() {
        return Command.findByNumber(readIntInput(">>"));
    }

    @Override
    public VoucherCommand readVoucherCommand() {
        return VoucherCommand.findByNumber(readIntInput(">>"));
    }

    @Override
    public CustomerCommand readCustomerCommand() {
        return CustomerCommand.findByNumber(readIntInput(">>"));
    }

    @Override
    public String readVoucherType() {
        return readStringInput("type (FIXED / PERCENT) >>");
    }

    @Override
    public int readDiscountAmount() {
        return readIntInput("discount amount >>");
    }

    @Override
    public String readNickname() {
        return readStringInput("nickname >>");
    }

    @Override
    public UUID readUUID() {
        String input = readStringInput("UUID >>");
        return UUID.fromString(input);
    }

    private int readIntInput(String prompt) {
        return TEXT_IO.newIntInputReader()
                .withInputTrimming(true)
                .read(prompt);
    }

    private String readStringInput(String prompt) {
        return TEXT_IO.newStringInputReader()
                .withInputTrimming(true)
                .read(prompt);
    }
}
