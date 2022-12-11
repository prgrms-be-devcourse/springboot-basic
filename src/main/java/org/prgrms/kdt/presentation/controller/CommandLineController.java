package org.prgrms.kdt.presentation.controller;

import org.prgrms.kdt.presentation.io.ConsoleIO;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);
    private final ConsoleIO consoleIO;
    private final VoucherService voucherService;
    private final BlackListService blackListService;

    public CommandLineController(ConsoleIO consoleIO, VoucherService voucherService, BlackListService blackListService) {
        this.consoleIO = consoleIO;
        this.voucherService = voucherService;
        this.blackListService = blackListService;
    }

    public void run(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            try {
                consoleIO.printEnableCommandList();
                CommandType command = CommandType.of(consoleIO.inputCommand());
                isRunning = command.executeCommand(consoleIO, voucherService, blackListService);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                logger.warn("{} {}", e.getMessage(), e.getStackTrace());
            }
        }
    }
}
