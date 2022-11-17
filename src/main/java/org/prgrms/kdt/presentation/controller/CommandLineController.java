package org.prgrms.kdt.presentation.controller;

import org.prgrms.kdt.presentation.io.ConsoleIO;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.VoucherValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        while (true) {
            consoleIO.printEnableCommandList();
            try {
                CommandType command = CommandType.of(consoleIO.inputCommand());
                switch (command) {
                    case CREATE -> {
                        String voucherType = consoleIO.inputVoucherType();
                        String voucherDiscountValue = consoleIO.inputVoucherDiscountValue();
                        VoucherValidator.validateVoucherTypeAndDiscountValue(voucherType, voucherDiscountValue);
                        voucherService.save(voucherService.create(voucherType, voucherDiscountValue));
                    }
                    case LIST -> consoleIO.printItems(voucherService.getAllVouchers().stream()
                            .map(voucher -> voucher.toString())
                            .collect(Collectors.toList()));
                    case BLACKLIST -> consoleIO.printItems(blackListService.getAllBlackList().stream()
                            .map(voucher -> voucher.toString())
                            .collect(Collectors.toList()));
                    case EXIT -> {
                        consoleIO.terminate();
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                logger.warn("{} {}", e.getMessage(), e.getStackTrace());
            }
        }
    }
}
