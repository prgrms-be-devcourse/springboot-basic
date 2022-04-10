package org.prgrms.weeklymission.application;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.weeklymission.console.Console;
import org.prgrms.weeklymission.customer.service.BlackCustomerService;
import org.prgrms.weeklymission.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static org.prgrms.weeklymission.utils.Command.*;

@Component
@Slf4j
public class Application implements Runnable {
    private final Console console;
    private final VoucherService voucherService;
    private final BlackCustomerService blackCustomerService;
    private volatile boolean isRun;

    @Autowired
    public Application(Console console, VoucherService voucherService, BlackCustomerService blackCustomerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.blackCustomerService = blackCustomerService;
    }

    @Override
    public void run() {
        isRun = true;
        log.info("program start...");
        while(isRun) {
            try {
                runApplication();
            } catch (Exception e) {
                log.error("program restart...");
                runApplication();
            }
        }
    }

    void runApplication() throws RuntimeException {
        console.initMessage();
        doActionByCommand(console.takeInput());
    }

    private void doActionByCommand(String command) {
        command = command.toLowerCase(Locale.ROOT);
        if(command.equals(EXIT.getValue())) {
            exit();
        } else if(command.equals(CREATE.getValue())) {
            voucherService.printCreateVoucher();
        } else if(command.equals(LIST.getValue())) {
            voucherService.printAllVouchers();
        } else if(command.equals(BLACK_REGISTER.getValue())) {
            blackCustomerService.printCreateBlackCustomer();
        } else if(command.equals(BLACK_LIST.getValue())) {
            blackCustomerService.printAllBlackCustomers();
        } else {
            log.error("Input Other Command: {}", command);
        }
    }

    private void exit() {
        console.exitMessage();
        voucherService.clearRepo();
        log.info("program exit...");
        isRun = false;
    }
}
