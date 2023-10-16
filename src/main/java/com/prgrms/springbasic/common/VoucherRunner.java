package com.prgrms.springbasic.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class VoucherRunner implements CommandLineRunner {
    private final MenuHandler menuHandler;
    private final Logger logger = LoggerFactory.getLogger(VoucherRunner.class);

    public VoucherRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;
        MenuType menuType;
        while (isRunning) {
            try {
                menuType = MenuType.find(menuHandler.chooseMode());
                switch (menuType) {
                    case EXIT -> isRunning = menuHandler.exit();
                    case CREATE -> menuHandler.createVoucher();
                    case LIST -> menuHandler.showAllVouchers();
                    case BLACK_LIST -> menuHandler.showAllBlackLists();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
