package org.prgrms.kdt.io;

import org.springframework.stereotype.Service;

@Service
public class Console {
    public static final String COMMAND_LIST_PROMPT = "=== Voucher Program ===" + System.lineSeparator() +
            "Type exit to exit the program." + System.lineSeparator() +
            "Type create to create a new voucher." + System.lineSeparator() +
            "Type list to list all vouchers" + System.lineSeparator();
    public static final String VOUCHER_TYPE_PROMPT = "Enter voucher type. (fixed or percent)" + System.lineSeparator();
    public static final String VOUCHER_AMOUNT_PROMPT = "Enter a value. (fixed - 0~ | percent - 0~100)" + System.lineSeparator();


    public static final String COMMAND_ERROR_PROMPT = "지원하지 않는 명령어입니다." + System.lineSeparator();
    public static final String VOUCHER_TYPE_ERROR_PROMPT = "지원하지 않는 바우처 종류입니다." + System.lineSeparator();
    public static final String VOUCHER_AMOUNT_NUMERIC_ERROR_PROMPT = "지원하지 않는 바우처 값입니다." + System.lineSeparator();

    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void printCommandError() {
        output.printText(COMMAND_ERROR_PROMPT);
    }

    public String getCommand() {
        output.printText(COMMAND_LIST_PROMPT);

        return input.inputText();
    }

    public String getType() {
        output.printText(VOUCHER_TYPE_PROMPT);

        return input.inputText();
    }

    public String getVoucherAmount() {
        output.printText(VOUCHER_AMOUNT_PROMPT);

        return input.inputText();
    }

    public void printVoucherTypeError() {
        output.printText(VOUCHER_TYPE_ERROR_PROMPT);
    }

    public void printVoucherAmountNumericError() {
        output.printText(VOUCHER_AMOUNT_NUMERIC_ERROR_PROMPT);
    }
}
