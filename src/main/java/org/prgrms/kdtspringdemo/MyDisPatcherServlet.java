package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.prgrms.kdtspringdemo.view.constant.ConsoleMessage.*;

@Component
public class MyDisPatcherServlet implements CommandLineRunner {
    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherService voucherService;

    public MyDisPatcherServlet(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) {
        MainCommandType userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT_MESSAGE);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT_MESSAGE);
        }
    }

    private void executeCommand(MainCommandType commandtype) {
        switch (commandtype) {
            case EXIT -> voucherConsole.printMessage(SYSTEM_SHUTDOWN_MESSAGE);
            case VOUCHER -> runVoucherService();
        }
    }

    private void runVoucherService() {
        VoucherCommandType userCommand = voucherConsole.inputVoucherCommand(VOUCHER_SERVICE_INIT_MESSAGE);

        switch (userCommand) {
            case CREATE -> createVoucher();
            case LIST_ALL -> getAllVoucher();
        }
    }

    private void createVoucher() {
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long amount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER_MESSAGE);

        VoucherDto voucherDto = voucherService.create(userVoucherType, amount);
        voucherConsole.printCreatedVoucher(SUCCESS_CREATED_VOUCHER, voucherDto.getVoucherType(), voucherDto.getAmount());
    }

    private void getAllVoucher() {
        List<VoucherDto> vouchers = voucherService.getAllVoucher();
        for (VoucherDto voucherDto : vouchers) {
            voucherConsole.printCreatedVoucher(SUCCESS_CREATED_VOUCHER, voucherDto.getVoucherType(), voucherDto.getAmount());
        }
    }
}
