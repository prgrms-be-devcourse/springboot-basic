package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;

public class StrategyExit implements Command {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        output.printOnExit();
        return false;
    }
}
