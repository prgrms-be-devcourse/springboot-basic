package com.demo.voucher;

import com.demo.voucher.controller.VoucherController;
import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.io.CommandType;
import com.demo.voucher.io.ConsoleView;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherCommandLineApp implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(VoucherCommandLineApp.class);

    private final ConsoleView consoleView;
    private final VoucherController controller;
    private boolean isProgramRunnable = true;


    @Override
    public void run(String... args) throws Exception {
        consoleView.showMenu();

        while (isProgramRunnable) {
            String inputCommand = consoleView.requestMenu();
            try {
                CommandType commandType = CommandType.of(inputCommand);
                switch (commandType) {
                    case EXIT -> exitProgram();
                    case CREATE -> createVoucher();
                    case LIST -> showVouchers();
                }
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                consoleView.inputError(e.getMessage());
            }
        }
    }

    private void exitProgram() {
        isProgramRunnable = false;
        consoleView.showExitMessage();
    }


    private void createVoucher() throws RuntimeException {
        consoleView.showVoucherType();
        String inputVoucherType = consoleView.requestVoucherType();

        VoucherType voucherType = VoucherType.of(inputVoucherType);

        String inputAmount = consoleView.getAmount(voucherType);
        if (voucherType.isValidAmount(inputAmount)) {
            controller.createVoucher(voucherType, inputAmount);
        }
    }

    private void showVouchers() {
        consoleView.showAllVouchers(controller.getAllVouchers());
    }

}
