package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.command.Command;
import org.prgrms.prgrmsspring.domain.command.WalletCommand;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.service.WalletService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController implements ApplicationController {
    private final CommandLineView commandLineView;
    private final WalletService walletService;

    public WalletController(CommandLineView commandLineView, WalletService walletService) {
        this.commandLineView = commandLineView;
        this.walletService = walletService;
    }

    public void create() {
        UUID customerId = commandLineView.inputCustomerId();
        UUID voucherId = commandLineView.inputVoucherId();
        Wallet wallet = walletService.allocateVoucherToCustomer(customerId, voucherId);
        commandLineView.print(wallet);
    }

    public void findCustomerVouchers() {
        UUID customerId = commandLineView.inputCustomerId();
        List<Voucher> voucherListByCustomerId = walletService.findVoucherListByCustomerId(customerId);
        commandLineView.printAll(voucherListByCustomerId);
    }

    public void deleteCustomerVouchers() {
        UUID customerId = commandLineView.inputCustomerId();
        walletService.deleteVouchersByCustomerId(customerId);
    }

    public void findCustomerHasVoucher() {
        UUID voucherId = commandLineView.inputVoucherId();
        Customer findCustomer = walletService.findCustomerByVoucherId(voucherId);
        commandLineView.print(findCustomer);
    }

    @Override
    public void run(String commandName) {
        Command command = Command.from(commandName, WalletCommand.class);
        command.run(this);
    }

    @Override
    public void listMode() {
        commandLineView.printAll(ApplicationController.getModeStrings(WalletCommand.values()));
    }
}
