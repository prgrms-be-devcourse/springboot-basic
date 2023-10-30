package org.prgrms.prgrmsspring.console;

import org.prgrms.prgrmsspring.controller.WalletController;
import org.prgrms.prgrmsspring.domain.command.Command;
import org.prgrms.prgrmsspring.domain.command.WalletCommand;
import org.prgrms.prgrmsspring.dto.WalletDto;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WalletConsole implements ConsoleIOManager {

    @Autowired
    private CommandLineView commandLineView;
    @Autowired
    private WalletController walletController;

    public void create() {
        UUID customerId = commandLineView.inputCustomerId();
        UUID voucherId = commandLineView.inputVoucherId();
        Wallet wallet = walletController.create(new WalletDto(customerId, voucherId));
        commandLineView.print(wallet);
    }

    public void findCustomerVouchers() {
        UUID customerId = commandLineView.inputCustomerId();
        List<Voucher> customerVouchers = walletController.findCustomerVouchers(customerId);
        commandLineView.printAll(customerVouchers);
    }

    public void deleteCustomerVouchers() {
        UUID customerId = commandLineView.inputCustomerId();
        walletController.deleteCustomerVouchers(customerId);
    }

    public void findCustomerHasVoucher() {
        UUID voucherId = commandLineView.inputVoucherId();
        Customer findCustomer = walletController.findCustomerHasVoucher(voucherId);
        commandLineView.print(findCustomer);
    }

    @Override
    public void run(String commandName) {
        Command command = Command.from(commandName, WalletCommand.class);
        command.run(this);
    }

    @Override
    public void printCommands() {
        List<String> modeStrings = this.getModeStrings(WalletCommand.values());
        commandLineView.printAll(modeStrings);
    }
}
