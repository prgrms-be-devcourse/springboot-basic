package com.prgrms.springbasic.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class VoucherRunner implements CommandLineRunner {
    private final MenuHandler menuHandler;
    private static final Logger logger = LoggerFactory.getLogger(VoucherRunner.class);

    public VoucherRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) {

        boolean isRunning = true;
        while (isRunning) {
            CommandType commandType;
            try {
                commandType = CommandType.find(menuHandler.chooseMode());
                switch (commandType) {
                    case EXIT -> isRunning = menuHandler.exit();
                    case CREATE -> menuHandler.createVoucher();
                    case LIST -> menuHandler.showAllVouchers();
                    case CREATE_CUSTOMER -> menuHandler.createCustomer();
                    case BLACK_LIST -> menuHandler.showAllBlackLists();
                    case UPDATE_VOUCHER -> menuHandler.updateVoucher();
                    case DELETE_ALL -> menuHandler.deleteAllVoucher();
                }
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
    }
}
