package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.user.domain.BannedCustomer;
import org.prgrms.kdt.user.service.UserService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StrategyBlackList implements Command {

    private final UserService userService;
    private final Output output;

    public StrategyBlackList(UserService userService, Console console) {
        this.userService = userService;
        this.output = console;
    }

    @Override
    public boolean excute() {
        List<BannedCustomer> blacklist = userService.getBlackList();
        output.blackList(blacklist);
        return true;
    }
}