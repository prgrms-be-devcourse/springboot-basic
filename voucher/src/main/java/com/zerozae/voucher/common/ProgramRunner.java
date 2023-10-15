package com.zerozae.voucher.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private final MenuHandler menuHandler;
    public ProgramRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) {
        while(true){
            MenuType command = MenuType.valueOf(menuHandler.showAndSelectCommand());

            boolean continueRun = true;
            switch (command){
                case VOUCHER -> {
                    continueRun = executeVoucherCommand();
                }
                case CUSTOMER -> {
                    continueRun = executeCustomerCommand();
                }
                case EXIT -> {
                    menuHandler.exit();
                    continueRun = false;
                }
            }
            if(!continueRun) break;
        }
    }
    private boolean executeVoucherCommand() {
        while(true){
            MenuType command = MenuType.valueOf(menuHandler.selectedVoucherProgram());
            switch (command) {
                case CREATE -> {
                    menuHandler.createVoucher();
                }
                case LIST -> {
                    menuHandler.voucherList();
                }
                case BACK -> {
                    return true;
                }
            }
        }
    }
    private boolean executeCustomerCommand() {
        while(true){
            MenuType command = MenuType.valueOf(menuHandler.selectedCustomerProgram());
            switch (command) {
                case CREATE -> {
                    menuHandler.createCustomer();
                }
                case LIST -> {
                    menuHandler.customerList();
                }
                case BLACKLIST -> {
                    menuHandler.customerBlacklist();
                }
                case BACK -> {
                    return true;
                }
            }
        }
    }
}
