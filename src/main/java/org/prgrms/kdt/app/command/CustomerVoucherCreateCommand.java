package org.prgrms.kdt.app.command;

import java.util.UUID;
import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.model.wallet.WalletVO;
import org.prgrms.kdt.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CustomerVoucherCreateCommand implements CommandOperator {

    private static final Logger logger = LoggerFactory
        .getLogger(CustomerVoucherCreateCommand.class);


    private final WalletService walletService;
    private final VoucherListCommand voucherListCommand;
    private final CustomerListCommand customerListCommand;
    private final Console console;

    public CustomerVoucherCreateCommand(WalletService walletService,
        VoucherListCommand voucherListCommand,
        CustomerListCommand customerListCommand, Console console) {
        this.walletService = walletService;
        this.voucherListCommand = voucherListCommand;
        this.customerListCommand = customerListCommand;
        this.console = console;
        this.commandType = CommandType.CUSTOMER_VOUCHER_CREATE;
    }

    private final CommandType commandType;

    @Override
    public void execute() {
        customerListCommand.execute();
        var customerId = UUID.fromString(console.input("Type a customer id"));

        voucherListCommand.execute();
        var voucherId = UUID.fromString(console.input("Type a voucher id"));

        var createdWallet = walletService.createWallet(new WalletVO(customerId, voucherId));
        logger.info("created wallet is {}", createdWallet);

    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
