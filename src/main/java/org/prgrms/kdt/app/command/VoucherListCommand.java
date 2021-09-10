package org.prgrms.kdt.app.command;

import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherListCommand implements CommandOperator {

    private final VoucherService voucherService;
    private final Console console;
    private final CommandType commandType;

    public VoucherListCommand(VoucherService voucherService,
        Console console) {
        this.voucherService = voucherService;
        this.console = console;
        this.commandType = CommandType.VOUCHER_LIST;
    }

    @Override
    public void execute() {
        var vouchers = voucherService.getAllVouchers();
        console.printMessage("=== Voucher List ===");
        if (!vouchers.isEmpty()) {
            vouchers.stream().map(Voucher::toString).forEach(console::printMessage);
        } else {
            console.printMessage("No Voucher Data");
        }
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
