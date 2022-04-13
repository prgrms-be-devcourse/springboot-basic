package com.prgms.management.command;

import com.prgms.management.command.io.CommandType;
import com.prgms.management.command.io.Console;
import com.prgms.management.customer.service.BlackCustomerService;
import com.prgms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final VoucherService voucherService;
    private final BlackCustomerService customerService;
    private final Console console;

    public CommandLineApplication(VoucherService voucherService, BlackCustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    @Override
    public void run() {
        while (true) {
            try {
                CommandType command = CommandType.of(console.getCommand());
                command.execute(voucherService, customerService, console);
            } catch (Exception e) {
                console.printString(e.getMessage());
                logger.error(e.getMessage());
            }
            console.printString("");
        }
    }
}
