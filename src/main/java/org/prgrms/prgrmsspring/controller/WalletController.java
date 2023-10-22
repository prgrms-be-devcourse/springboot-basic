package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.Command;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.service.WalletService;
import org.prgrms.prgrmsspring.view.CommandLineView;

import java.util.List;
import java.util.UUID;

public class WalletController implements ApplicationController {
    private final CommandLineView commandLineView;
    private final WalletService walletService;

    public WalletController(CommandLineView commandLineView, WalletService walletService) {
        this.commandLineView = commandLineView;
        this.walletService = walletService;
    }

    @Override
    public void start(Command command) {
        command.run(this);
    }

    public void create() {
        UUID customerId = commandLineView.inputCustomerId();
        UUID voucherId = commandLineView.inputVoucherId();
        Wallet wallet = walletService.allocateVoucherToCustomer(customerId, voucherId);
        commandLineView.print(wallet);
    }

    public void findVoucherByCustomerId() {
        UUID customerId = commandLineView.inputCustomerId();
        List<Voucher> voucherListByCustomerId = walletService.findVoucherListByCustomerId(customerId);
        commandLineView.printAll(voucherListByCustomerId);
    }
}
