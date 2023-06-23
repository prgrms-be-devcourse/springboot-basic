package org.prgms.voucher.view;

import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgms.voucher.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ConsoleVoucherView implements VoucherView {

    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal terminal = TextIoFactory.getTextIO().getTextTerminal();

    private final String[] options = {"exit", "create", "list"};
    private final String[] voucherTypes = {"FixedAmount", "PercentDiscount"};

    @Override
    public void printOptions() {
        terminal.println("=== Voucher Program ===");
        terminal.println("Type exit to exit the program.");
        terminal.println("Type create to create a new voucher.");
        terminal.println("Type list to list all vouchers.");
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> terminal.println(voucher.toString()));
    }

    @Override
    public void printError(String message) {
        terminal.println(message);
    }

    @Override
    public String readChoice() {
        return textIO
                .newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues(options)
                .read();
    }

    @Override
    public String readVoucherType() {
        return textIO
                .newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues(voucherTypes)
                .read();
    }

    @Override
    public long readAmount() {
        return textIO
                .newLongInputReader()
                .withInputTrimming(true)
                .withMinVal(0L)
                .read(" Amount : ");
    }

    @Override
    public long readPercentage() {
        return textIO.newLongInputReader()
                .withInputTrimming(true)
                .withMinVal(0L)
                .withMaxVal(100L)
                .read(" Percentage : ");
    }

}
