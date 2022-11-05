package org.prgrms.kdt.io;

import org.springframework.stereotype.Service;

@Service
public class Console {
    public static final String COMMAND_LIST_PROMPT = "=== Voucher Program ===" + System.lineSeparator() +
            "Type exit to exit the program." + System.lineSeparator() +
            "Type create to create a new voucher." + System.lineSeparator() +
            "Type list to list all vouchers" + System.lineSeparator();
    public static final String COMMAND_ERROR_PROMPT = "지원하지 않는 명령어입니다." + System.lineSeparator();

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
        return null;
    }

    public String getVoucherAmount() {
        return null;
    }

    public void printVoucherTypeError() {

    }

    public void printVoucherAmountNumericError() {

    }

    public void printVoucherAmountOutOfBoundError() {

    }
}
