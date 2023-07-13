package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.global.model.CommandMenu;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InputConsole {

    private static final TextIO textIO = TextIoFactory.getTextIO();

    public CommandMenu readCommandMenu() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("CommandMenu Selection: ");
        return CommandMenu.getCommandMenu(input);
    }

    public VoucherType readVoucherType() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Voucher Type Selection: ");
        return VoucherType.getVoucherType(input);
    }

    public DiscountValue readDiscountValue(VoucherType voucherType) {
        String inputDiscountValue = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Discount Value: ");
        return new DiscountValue(voucherType, inputDiscountValue);
    }

    public UUID readId() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Id: ");
        return UUID.fromString(input);
    }

    public String readName() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Name: ");
    }

    public boolean readBlack() {
        return textIO.newBooleanInputReader()
                .read("Black Customer: ");
    }

}
