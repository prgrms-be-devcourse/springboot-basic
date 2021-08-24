package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.user.domain.BannedCustomer;
import org.prgrms.kdt.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceBlackList implements Command {

    private final UserService userService;
    private final Output output;

    public ServiceBlackList(UserService userService, Console console) {
        this.userService = userService;
        this.output = console;
    }

    @Override
    public boolean execute() {
        List<BannedCustomer> blacklist = userService.getBlackList();
        output.blackList(blacklist);
        return true;
    }
}