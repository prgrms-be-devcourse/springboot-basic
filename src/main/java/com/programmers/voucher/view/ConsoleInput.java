package com.programmers.voucher.view;

import com.programmers.voucher.entity.voucher.VoucherType;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput implements Input {
    private static final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public Command readCommand() {
        return Command.findByNumber(readIntInput(">>"));
    }

    @Override
    public VoucherCommand readVoucherCommand() {
        return VoucherCommand.findByNumber(readIntInput(">>"));
    }

    @Override
    public VoucherType readVoucherType() {
        return VoucherType.findByNumber(readIntInput(">>"));
    }

    @Override
    public DiscountAmount readDiscountAmount(VoucherType voucherType) {
        int input = readIntInput("discount amount >>");
        return new DiscountAmount(voucherType, input);
    }

    private int readIntInput(String prompt) {
        return textIO.newIntInputReader()
                .withInputTrimming(true)
                .read(prompt);
    }
}
