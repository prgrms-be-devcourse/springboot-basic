package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.voucher.constant.CommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {
    private final VoucherConsole voucherConsole;
    private final VoucherService voucherService;

    @Autowired
    public CommandLineApplication(VoucherConsole voucherConsole, VoucherService voucherService) {
        this.voucherConsole = voucherConsole;
        this.voucherService = voucherService;
    }

    public void run() {
        String userCommand = "";

        while (!userCommand.equals(CommandType.EXIT.name())) {
            voucherConsole.printInitMessage();
            userCommand = voucherConsole.InputCommand().toUpperCase();
            CommandType commandType = CommandType.findCommandType(userCommand);
            executeCommand(commandType);
        }
    }

    private void executeCommand(CommandType commandtype) {
        switch (commandtype) {
            case EXIT -> {
                voucherConsole.printSystemShutdown();
            }
            case CREATE -> {
                String userVoucherType = voucherConsole.ChooseVoucherType().toUpperCase();
                VoucherType voucherType = VoucherType.findVoucherType(userVoucherType);
                Long discount = voucherConsole.InputDiscountByVoucher();
                Voucher voucher = voucherService.createVoucher(voucherType, discount);
                voucherConsole.printCreatedVoucher(voucher);
            }
            case LIST -> {
                //List Logic
            }
            default -> {
                voucherConsole.printInvalidCommandSelected();
            }
        }
    }
}
