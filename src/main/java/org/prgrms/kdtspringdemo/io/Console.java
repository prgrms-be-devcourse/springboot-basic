package org.prgrms.kdtspringdemo.io;

import org.prgrms.kdtspringdemo.voucher.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class Console {
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getInputWithPrompt(String prompt) {
        output.printPrompt(prompt);
        return input.getInput();
    }

    public void showMenu() {
        output.printText("=== Voucher Program ===");
        output.printText("Type exit to exit the program.");
        output.printText("Type create to create a new voucher.");
        output.printText("Type list to list all vouchers.");
    }

    public VoucherType selectVoucherTypeMenu() {
        output.printText("=== Create Voucher ===");
        output.printText("Type fixed to create fixed voucher");
        output.printText("Type percent to create fixed voucher");
        return VoucherType.getTypeByName(input.getInput());
    }

    public Long getVoucherValue() throws NumberFormatException{
        output.printText("=== Type Number ===");
        return Long.parseLong(input.getInput());
    }

    public Exception showError(Exception e) {
        output.printError(e);
        return e;
    }

}
