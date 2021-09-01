package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

public class ExitCommand implements CommandStrategy {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        output.exit();
        return false;
    }
}
