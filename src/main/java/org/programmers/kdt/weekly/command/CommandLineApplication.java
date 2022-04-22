package org.programmers.kdt.weekly.command;

import java.util.List;
import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.InputErrorType;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final VoucherWalletCommandLine voucherWalletCommandLine;
    private final VoucherService voucherService;
    private final Console console;
    private final CustomerCommandLine customerCommand;

    public CommandLineApplication(
        VoucherWalletCommandLine voucherWalletCommandLine,
        VoucherService voucherService,
        Console console,
        CustomerCommandLine customerCommandLine) {
        this.voucherWalletCommandLine = voucherWalletCommandLine;
        this.voucherService = voucherService;
        this.console = console;
        this.customerCommand = customerCommandLine;
    }

    public void run() {
        CommandType commandType = CommandType.DEFAULT;

        while (commandType != CommandType.EXIT) {
            this.console.printVoucherMessage();
            var userInput = this.console.getUserInput();

            try {
                commandType = CommandType.findByCommand(userInput);
            } catch (IllegalArgumentException e) {
                logger.debug("잘못된 사용자 입력 -> {}", userInput);
            }

            switch (commandType) {
                case VOUCHER_CREATE -> createVoucher();
                case VOUCHER_LIST -> showVoucherList();
                case CUSTOMER -> this.customerCommand.run();
                case WALLET -> this.voucherWalletCommandLine.run();
                case EXIT -> this.console.programExitMessage();
                default -> this.console.printInputErrorMessage(InputErrorType.COMMAND);
            }
            this.console.newLinePrint();
        }
    }

    private void createVoucher() {
        this.console.printVoucherSelectMessage();

        try {
            int selectVoucherType = Integer.parseInt(console.getUserInput());
            VoucherType voucherType = VoucherType.findByNumber(selectVoucherType);
            this.console.printVoucherDiscountSelectMessage();
            voucherService.createVoucher(voucherType,
                Integer.parseInt(console.getUserInput()));
            this.console.printExecutionSuccessMessage();
        } catch (IllegalArgumentException e) {
            logger.error("voucher number type input error");
            this.console.printInputErrorMessage(InputErrorType.INVALID);
        }
    }

    private void showVoucherList() {
        List<Voucher> vouchers = voucherService.getVoucherList();

        if (vouchers.size() > 0) {
            vouchers.forEach((v) -> System.out.println(v.toString()));
            return;
        }
        this.console.printInputErrorMessage(InputErrorType.VOUCHER_EMPTY);
    }
}