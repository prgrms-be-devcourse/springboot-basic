package org.prgrms.kdt.app.command;

import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Component;


@Component
public class VoucherDeleteCommand implements CommandOperator {

    private final VoucherService voucherService;
    private final Console console;
    private final CommandType commandType;

    public VoucherDeleteCommand(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
        this.commandType = CommandType.DELETE_ALL_VOUCHER;
    }

    @Override
    public void execute() {
        console.printMessage("=== Delete All Vouchers ===");
        console.printMessage("Are you sure you want to delete all vouchers?");
        console.input("Type y(yes): ", s -> s.equals("y"));
        voucherService.deleteAllVouchers();
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
