package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;

public class ExitCommand implements Command {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        return false;
    }
}
