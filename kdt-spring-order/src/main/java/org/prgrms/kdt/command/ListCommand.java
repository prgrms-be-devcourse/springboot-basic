package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;

public class ListCommand implements Command {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        return true;
    }
}
