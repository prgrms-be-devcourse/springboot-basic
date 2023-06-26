package com.programmers.voucher.view;

import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ConsoleInput implements Input {
    private static final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public Command readCommand() {
        String input = textIO.newStringInputReader() //TODO 반복 코드 개선
                .withInputTrimming(true)
                .read(">>");

        return Command.findByCode(input);
    }

    @Override
    public VoucherCommand readVoucherCommand() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read(">>");

        return VoucherCommand.findByCode(input);
    }

    @Override
    public DiscountAmount readDiscountAmount(VoucherCommand voucherCommand) {
        Long input = textIO.newLongInputReader()
                .withInputTrimming(true)
                .read("discount amount >>");

        return new DiscountAmount(voucherCommand, input);
    }
}
