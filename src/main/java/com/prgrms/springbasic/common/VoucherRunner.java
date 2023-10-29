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
            MenuType menuType;
            try {
                menuType = MenuType.find(menuHandler.chooseMenu());

                switch (menuType) {
                    case VOUCHER -> runVoucher(menuType);
                    case CUSTOMER -> runCustomer(menuType);
                    case WALLET -> runWallet(menuType);
                    case EXIT -> isRunning = menuHandler.exit();
                }
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
    }

    private void runVoucher(MenuType menuType) {
        try {
            CommandType commandType = CommandType.find(menuHandler.chooseMode(menuType));
            switch (commandType) {
                case CREATE -> menuHandler.createVoucher();
                case LIST -> menuHandler.showAllVouchers();
                case UPDATE -> menuHandler.updateVoucher();
                case DELETE_ALL -> menuHandler.deleteAllVoucher();
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private void runCustomer(MenuType menuType) {
        try {
            CommandType commandType = CommandType.find(menuHandler.chooseMode(menuType));
            switch (commandType) {
                case CREATE -> menuHandler.createCustomer();
                case LIST -> menuHandler.showAllCustomer();
                case BLACK_LIST -> menuHandler.showAllBlackLists();
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private void runWallet(MenuType menuType) {
        try {
            CommandType commandType = CommandType.find(menuHandler.chooseMode(menuType));
            switch (commandType) {
                case CREATE -> menuHandler.createWallet();
                case FIND_VOUCHERS -> menuHandler.showAllCustomerVouchers();
                case FIND_CUSTOMERS -> menuHandler.showAllVoucherCustomers();
                case DELETE -> menuHandler.deleteWallet();
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }
}
