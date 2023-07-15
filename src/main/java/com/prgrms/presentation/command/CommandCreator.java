package com.prgrms.presentation.command;


import com.prgrms.presentation.Menu;
import com.prgrms.presentation.command.voucher.CreateCommand;
import com.prgrms.presentation.command.voucher.ExitCommand;
import com.prgrms.presentation.command.voucher.ListCommand;
import com.prgrms.presentation.command.wallet.CustomerListCommand;
import com.prgrms.presentation.command.wallet.GiveVoucherCommand;
import com.prgrms.presentation.command.wallet.TakeVoucherCommand;
import com.prgrms.presentation.command.wallet.VoucherListCommand;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.service.voucher.VoucherService;
import com.prgrms.service.wallet.WalletService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandCreator {

    private final Map<Menu, Command> strategies;
    private final WalletService walletService;
    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    public CommandCreator(WalletService walletService, VoucherService voucherService, Input input, Output output) {
        this.input = input;
        this.output = output;
        strategies = new HashMap<>();
        this.voucherService = voucherService;
        this.walletService = walletService;

        strategies.put(Menu.EXIT, new ExitCommand(this.output));
        strategies.put(Menu.LIST, new ListCommand(this.output, voucherService));
        strategies.put(Menu.CREATE, new CreateCommand(this.input, this.output, voucherService));

        strategies.put(Menu.GIVE_VOUCHER, new GiveVoucherCommand(this.output, this.input, walletService));
        strategies.put(Menu.TAKE_VOUCHER, new TakeVoucherCommand(this.output, this.input, walletService));
        strategies.put(Menu.CUSTOMER_LIST, new CustomerListCommand(this.output, this.input, walletService));
        strategies.put(Menu.VOUCHER_LIST, new VoucherListCommand(this.input, this.output, walletService));
    }

    public Command createCommand(Menu menu) {
        return strategies.get(menu);
    }
}






