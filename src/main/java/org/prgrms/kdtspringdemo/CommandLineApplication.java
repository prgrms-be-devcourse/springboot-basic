package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.console.input.ScannerInput;
import org.prgrms.kdtspringdemo.view.console.output.PrintOutput;
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
    private final VoucherConsole voucherConsole = new VoucherConsole(new ScannerInput(), new PrintOutput());
    private final VoucherService voucherService;

    public CommandLineApplication(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) {
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
                VoucherDto voucher = createVoucher(userVoucherType);
                voucherConsole.printCreatedVoucher(voucher);
            }
            case LIST -> {
                List<VoucherDto> vouchers = voucherService.getAllVoucher();
                vouchers.forEach(voucherConsole::printCreatedVoucher);
            }
            default -> {
                voucherConsole.printInvalidCommandSelected();
            }
        }
    }

    private VoucherDto createVoucher(String userVoucherType) {
        VoucherType voucherType = VoucherType.findVoucherType(userVoucherType);
        Long discount = voucherConsole.inputDiscountByVoucher();
        VoucherDto voucherDto = new VoucherDto(voucherType, discount);
        return voucherService.create(voucherDto);
    }
}
