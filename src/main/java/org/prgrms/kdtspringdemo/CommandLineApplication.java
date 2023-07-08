package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.voucher.constant.CommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private static final String INIT_MESSAGE = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """;
    private static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.\n";
    private static final String CHOICE_VOUCHER_TYPE_MESSAGE = "바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n";

    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherService voucherService;

    public CommandLineApplication(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) {
        CommandType userCommand = voucherConsole.inputCommand(INIT_MESSAGE);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = voucherConsole.inputCommand(INIT_MESSAGE);
        }
    }

    private void executeCommand(CommandType commandtype) {
        switch (commandtype) {
            case EXIT -> voucherConsole.printMessage(SYSTEM_SHUTDOWN_MESSAGE);
            case CREATE -> createVoucher();
            case LIST -> getAllVoucher();
        }
    }

    private void getAllVoucher() {
        List<VoucherDto> vouchers = voucherService.getAllVoucher();
        for (VoucherDto voucherDto : vouchers) {
            voucherConsole.printCreatedVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());
        }
    }

    private void createVoucher() {
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long amount = voucherConsole.inputAmountByVoucher();

        VoucherDto voucherDto = voucherService.create(userVoucherType, amount);
        voucherConsole.printCreatedVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());
    }
}
