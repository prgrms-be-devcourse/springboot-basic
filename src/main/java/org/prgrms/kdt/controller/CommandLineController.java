package org.prgrms.kdt.controller;

import org.prgrms.kdt.io.ConsoleIO;
import org.prgrms.kdt.model.customer.service.CustomerService;
import org.prgrms.kdt.model.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);
    private final ConsoleIO consoleIO;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineController(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
        this.consoleIO = consoleIO;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            try {
                consoleIO.printEnableCommandList();
                CommandType command = CommandType.of(consoleIO.inputCommand());
                isRunning = command.executeCommand(consoleIO, voucherService, customerService);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                logger.warn("{} {}", e.getMessage(), e.getStackTrace());
            }
        }
    }
}
