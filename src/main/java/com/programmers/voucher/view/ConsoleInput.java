package com.programmers.voucher.view;

import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ConsoleInput implements Input {
    private static final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public Command readCommand() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read(">>");

        return Command.findByName(input);
    }

    @Override
    public VoucherType readVoucherType() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read(">>");

        return VoucherType.findByName(input);
    }

    @Override
    public DiscountAmount readDiscountAmount(VoucherType voucherType) {
        Long input = textIO.newLongInputReader()
                .withInputTrimming(true)
                .read("discount amount >>");

        return new DiscountAmount(voucherType, input);
    }
}
