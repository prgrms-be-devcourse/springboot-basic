package com.prgrms.springbasic.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class VoucherRunner implements CommandLineRunner {
    private final MenuHandler menuHandler;

    public VoucherRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;
        MenuType menuType;
        while (isRunning) {
            menuType = MenuType.find(menuHandler.chooseMode());
            switch (menuType) {
                case EXIT -> isRunning = menuHandler.exit();
                case CREATE -> menuHandler.createVoucher();
                case LIST -> menuHandler.showAllVouchers();
                case BLACK_LIST -> menuHandler.showAllBlackLists();
            }
        }
    }
}
