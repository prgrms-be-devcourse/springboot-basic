package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.voucher.constant.CommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication {
    private final VoucherConsole voucherConsole;
    private final VoucherService voucherService;

    public CommandLineApplication(VoucherConsole voucherConsole, VoucherService voucherService) {
        this.voucherConsole = voucherConsole;
        this.voucherService = voucherService;
    }

    public void run() {
        CommandType userCommand = CommandType.NONE;

        while (!userCommand.equals(CommandType.EXIT)) {
            voucherConsole.printInitMessage();
            userCommand = CommandType.findCommandType(voucherConsole.inputCommand().toUpperCase());
            executeCommand(userCommand);
        }
    }

    private void executeCommand(CommandType commandtype) {
        switch (commandtype) {
            case EXIT -> {
                voucherConsole.printSystemShutdown();
            }
            case CREATE -> {
                String userVoucherType = voucherConsole.chooseVoucherType().toUpperCase();
                VoucherType voucherType = VoucherType.findVoucherType(userVoucherType);
                Long discount = voucherConsole.inputDiscountByVoucher();
                Voucher voucher = voucherService.createVoucher(voucherType, discount);
                voucherConsole.printCreatedVoucher(voucher);
            }
            case LIST -> {
                List<Voucher> vouchers = voucherService.getAllVoucher();
                vouchers.forEach(voucherConsole::printCreatedVoucher);
            }
            default -> {
                voucherConsole.printInvalidCommandSelected();
            }
        }
    }
}
