package com.prgrms.springbootbasic.console;

import com.prgrms.springbootbasic.voucher.VoucherType;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

@Component
public class Console {

    private static final String COMMAND_NOT_SUPPORTER = "Command not supported yet.";
    private static final String MENU = "=== Voucher Program ===\n" +
            "Type **exit** to exit the program.\n" +
            "Type **create** to create a new voucher.\n" +
            "Type **list** to list all vouchers.";
    private static final String EXIT_MESSAGE = "Exit program. Bye.";
    private static final String TYPE_VOUCHER_MESSAGE = "Type 'fixed amount' for fixed amount voucher, or type 'percent' for percent voucher";
    private static final String TYPE_FIXED_AMOUNT_MESSAGE = "Chose fixed amount. Type fixed amount(1 ~ 10000). Amount must be an integer.";
    private static final String TYPE_PERCENT_MESSAGE = "Chose percent. Type percent amount(1 ~ 99(%)). Amount must be an integer.";
    private static final String FIXED_AMOUNT_OUT_OF_BOUND = "{0} is out of bound 1 ~ 10000.";
    private static final String PERCENT_OUT_OF_BOUND = "{0} is out of bound 1 ~ 99.";

    private final Reader reader;
    private final Printer printer;

    public Console(Reader reader, Printer printer) {
        this.reader = reader;
        this.printer = printer;
    }

    public String getCommand() {
        return reader.read();
    }

    public String getInput() {
        return reader.read();
    }

    public void printCommendNotSupported() {
        printer.printMessage(COMMAND_NOT_SUPPORTER);
    }

    public void printMenu() {
        printer.printMessage(MENU);
    }

    public void printExitMessage() {
        printer.printMessage(EXIT_MESSAGE);
    }

    public void printChoosingVoucher() {
        printer.printMessage(TYPE_VOUCHER_MESSAGE);
    }

    public void printDiscountAmountMessage(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED_AMOUNT ->
                    printer.printMessage(MessageFormat.format(TYPE_FIXED_AMOUNT_MESSAGE, voucherType.getInputValue()));
            case PERCENT ->
                    printer.printMessage(MessageFormat.format(TYPE_PERCENT_MESSAGE, voucherType.getInputValue()));
        }
    }

    public void printAmountOutOfBoundMessage(VoucherType voucherType, String exceptionMessage) {
        switch (voucherType) {
            case FIXED_AMOUNT ->
                    printer.printMessage(MessageFormat.format(FIXED_AMOUNT_OUT_OF_BOUND, exceptionMessage));
            case PERCENT -> printer.printMessage(MessageFormat.format(PERCENT_OUT_OF_BOUND, exceptionMessage));
        }
    }

    public void printExceptionMessage(String exceptionMessage) {
        printer.printMessage(exceptionMessage);
    }
}
