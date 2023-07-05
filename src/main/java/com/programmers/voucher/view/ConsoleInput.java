package com.programmers.voucher.view;

import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import com.programmers.voucher.view.dto.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput implements Input {
    private static final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public Command readCommand() {
        return Command.findByNumber(readIntInput());
    }

    @Override
    public VoucherCommand readVoucherCommand() {
        return VoucherCommand.findByNumber(readIntInput());
    }

    @Override
    public VoucherType readVoucherType() {
        return VoucherType.findByNumber(readIntInput());
    }

    @Override
    public DiscountAmount readDiscountAmount(VoucherType voucherType) {
        Long input = textIO.newLongInputReader()
                .withInputTrimming(true)
                .read("discount amount >>");

        return new DiscountAmount(voucherType, input);
    }

    private int readIntInput() {
        return textIO.newIntInputReader()
                .withInputTrimming(true)
                .read(">>");
    }
}
