package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.io.OutputConsole;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

public class ListCommand implements CommandStrategy {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        output.printVouchers(voucherService.listVouchers());
        return true;
    }
}
