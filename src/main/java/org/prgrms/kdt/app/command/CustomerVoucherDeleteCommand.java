package org.prgrms.kdt.app.command;

import java.util.UUID;
import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.service.WalletService;
import org.springframework.stereotype.Component;


@Component
public class CustomerVoucherDeleteCommand implements CommandOperator {

    private final CommandType commandType;
    private final WalletService walletService;
    private final Console console;
    private final CustomerVoucherListCommand customerVoucherListCommand;
    private final CustomerListCommand customerListCommand;


    public CustomerVoucherDeleteCommand(
        WalletService walletService,
        Console console,
        CustomerVoucherListCommand customerVoucherListCommand,
        CustomerListCommand customerListCommand) {
        this.walletService = walletService;
        this.console = console;
        this.customerVoucherListCommand = customerVoucherListCommand;
        this.customerListCommand = customerListCommand;
        this.commandType = CommandType.CUSTOMER_VOUCHER_DELETE;
    }

    @Override
    public void execute() {
        customerListCommand.execute();
        var customerId = UUID.fromString(console.input("조회할 고객의 아이디를 입력해주세요"));
        customerVoucherListCommand.execute(customerId);
        var voucherId = UUID.fromString(console.input("삭제할 바우처 아이디를 입력해주세요"));
        walletService.deleteWallet(customerId, voucherId);
        customerVoucherListCommand.execute(customerId);
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
