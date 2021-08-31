package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;

import java.util.UUID;

public class CreateCommand implements Command {
    private static final String CURSOR = "> ";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        try {
            output.voucherSelectType();
            String voucherType = input.inputVoucherType(CURSOR);
            long value = Long.parseLong((input.inputValue("input value [fixed amount | percent discount] " + CURSOR)));
            voucherService.createVoucher(voucherType, UUID.randomUUID(), value);
        } catch (NumberFormatException e) {
            output.invalidNumberInput();
        } catch (IllegalArgumentException e) {
            output.invalidVoucherTypeInput();
        }
        return true;
    }
}
