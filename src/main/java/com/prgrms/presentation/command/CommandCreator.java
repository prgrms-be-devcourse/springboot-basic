package com.prgrms.presentation.command;


import com.prgrms.presentation.Menu;
import com.prgrms.service.voucher.VoucherService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandCreator {

    private final Map<Menu, Command> strategies;
    private final VoucherService voucherService;

    public CommandCreator(VoucherService voucherService) {
        strategies = new HashMap<>();
        this.voucherService = voucherService;

        strategies.put(Menu.EXIT, new ExitCommand());
        strategies.put(Menu.LIST, new ListCommand(voucherService));
        strategies.put(Menu.CREATE, new CreateCommand(voucherService));
    }

    public Command createCommand(Menu menu) {
        return strategies.get(menu);
    }
}






