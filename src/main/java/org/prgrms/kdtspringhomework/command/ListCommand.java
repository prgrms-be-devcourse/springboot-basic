package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

public class ListCommand implements CommandStrategy {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        voucherService.printVouchers();
        return true;
    }
}
