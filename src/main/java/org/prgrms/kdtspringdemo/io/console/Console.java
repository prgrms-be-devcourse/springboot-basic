package org.prgrms.kdtspringdemo.io.console;

import org.prgrms.kdtspringdemo.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

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

    public String showList(List<Voucher> voucherList) {
        String voucherListString =voucherList.stream().map((voucher) -> {
            String[] argument = {
                    voucher.getVoucherId().toString(),
                    voucher.discountValue(),
                    voucher.getVoucherType().name().toLowerCase()

            };
            return MessageFormat.format("  {0}  |  {1}  |  {2} |",argument);
        }).collect(Collectors.joining(
                "\n"
                ,"  ID  |  Amount  |  VoucherType  |\n"
                ,"\n==========================="));
        output.printText(voucherListString);
        return voucherListString;
    }
}
