package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class StrategyExit implements Command {

    private final Output output;

    public StrategyExit(Console console) {
        this.output = console;
    }


    @Override
    public boolean excute() {
        output.exit();
        return false;
    }
}
