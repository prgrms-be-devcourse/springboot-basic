package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.user.service.UserService;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

public class StrategyBlackList implements Command {
    @Override
    public boolean excute(Input input, Output output, VoucherService voucherService) {

        return true;
    }
}