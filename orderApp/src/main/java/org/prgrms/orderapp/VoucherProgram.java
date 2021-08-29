package org.prgrms.orderapp;

import org.prgrms.orderapp.io.Console;
import org.prgrms.orderapp.model.Voucher;
import org.prgrms.orderapp.model.VoucherType;
import org.prgrms.orderapp.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class VoucherProgram implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(VoucherProgram.class);
    private final String CREATE = "create";
    private final String LIST = "list";
    private final String EXIT = "exit";
    private final VoucherService voucherService;
    private final Console console;

    public VoucherProgram(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void run() {
        logger.info("Voucher Program started.");
        String prompt = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """;
        String input;
        while (true) {
            input = console.input(prompt);
            switch (input) {
                case CREATE -> {
                    logger.info("Starts user command: 'create'");
                    createVoucher();
                    logger.info("Finished user command: 'create'");
                }
                case LIST -> {
                    logger.info("Starts user command: 'list'");
                    console.vouchers(voucherService.getAllVoucher());
                    logger.info("Finished user command: 'list'");
                }
                case EXIT -> {
                    logger.info("Starts user command: 'exit'");
                    console.printMessage("Exiting the program.");
                    logger.info("Finished user command: 'exit'");
                    return;
                }
                default -> {
                    logger.warn("Undefined user command: %s".formatted(input));
                    console.inputError(input);
                }
            }
            console.printMessage("====================================");
            logger.info("Command finished.");
            prompt = "Please type a command : ";
        }
    }

    private void createVoucher() {
        String type = console.input("Creating a new voucher. Which type do you want? (fixed or percent) : ");
        String amount = console.input("How much discount? (digits only) : ");
        logger.info("User input for createVoucher: {} {}", type, amount);
        Optional<Voucher> voucher = voucherService.createVoucher(type, amount);

        if (voucher.isPresent()) {
            voucherService.saveVoucher(voucher.get());
            console.printMessage("Successfully created a voucher");
            logger.info("Successfully created a voucher");
        } else {
            console.printMessage("Fail to create a voucher. Please provide appropriate types and values");
            logger.warn("Fail to create a voucher. User input: {} {}", type, amount);
        }
    }
}
