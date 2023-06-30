package org.prgms.voucher.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgms.voucher.dto.BlackCustomerResponseDto;
import org.prgms.voucher.dto.VoucherResponseDto;
import org.prgms.voucher.option.Option;
import org.prgms.voucher.voucher.VoucherPolicy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsoleVoucherView implements VoucherView {

    private final static TextIO textIO = TextIoFactory.getTextIO();
    private final static TextTerminal terminal = TextIoFactory.getTextIO().getTextTerminal();

    private final static String[] options = Arrays.stream(Option.values())
            .map(Option::getOption)
            .toArray(String[]::new);
    private final static String[] voucherTypes = Arrays.stream(VoucherPolicy.values())
            .map(VoucherPolicy::getVoucherPolicy)
            .toArray(String[]::new);

    public void printOptions() {
        terminal.println("=== Voucher Program ===");
        terminal.println(
                Arrays.stream(Option.values())
                        .map(Option::getInfo)
                        .collect(Collectors.joining(System.lineSeparator()))
        );
    }

    @Override
    public void printVouchers(List<VoucherResponseDto> vouchers) {
        terminal.println("=== Voucher list ====");
        terminal.println(
                vouchers.stream()
                        .map(VoucherResponseDto::toString)
                        .collect(Collectors.joining(System.lineSeparator()))
        );
    }

    @Override
    public void printError(String message) {
        terminal.println(message);
    }

    @Override
    public void printCustomers(List<BlackCustomerResponseDto> customers) {
        terminal.println("=== Black list ===");
        terminal.println(
                customers.stream()
                        .map(BlackCustomerResponseDto::toString)
                        .collect(Collectors.joining(System.lineSeparator()))
        );
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

}
