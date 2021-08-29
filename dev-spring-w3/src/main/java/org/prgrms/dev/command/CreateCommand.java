package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;

import java.util.InputMismatchException;

public class CreateCommand implements Command {

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        try {
            output.voucherSelectType();
            String voucherType = input.inputVoucherType("> ");
            Long value = Long.valueOf(input.inputValue("input value [fixed amount | percent discount] > "));
            voucherService.createVoucher(voucherType, value);
        } catch (NumberFormatException e) {
            output.invalidNumberInput();
        } catch (IllegalArgumentException e){
            output.invalidVoucherTypeInput();
        }
        return true;
    }
}
