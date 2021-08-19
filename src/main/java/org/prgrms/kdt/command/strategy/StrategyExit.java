package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;

public class StrategyExit implements Command {
    @Override
    public boolean excute(Input input, Output output, VoucherService voucherService) {
        output.exit();
        return false;
    }
}
