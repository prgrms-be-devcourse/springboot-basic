package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;

public class CreateCommand implements Command {

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        output.voucherSelectType();
        String type = input.input("> ");
        Long value = Long.valueOf(
                input.input("input value [fixed amount | percent discount] > "));
        voucherService.createVoucher(type, value);
        return true;
    }
}
