package com.zerozae.voucher.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private static final String MAIN_PROGRAM = "mainProgram";
    private static final String CUSTOMER_PROGRAM = "customerProgram";
    private static final String VOUCHER_PROGRAM = "voucherProgram";
    private final MenuHandler menuHandler;

    public ProgramRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) {
        boolean continueRun = true;
        while(continueRun){
            try{
                MenuType command = menuHandler.selectCommand(MAIN_PROGRAM);

                switch (command){
                    case VOUCHER -> {
                        continueRun = isVoucherMenuSelected();
                    }
                    case CUSTOMER -> {
                        continueRun = isCustomerMenuSelected();
                    }
                    case EXIT -> {
                        menuHandler.exit();
                        continueRun = false;
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isVoucherMenuSelected() {
        while(true){
            try{
                MenuType command = menuHandler.selectCommand(VOUCHER_PROGRAM);
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isCustomerMenuSelected() {
        while(true){
            try{
                MenuType command = menuHandler.selectCommand(CUSTOMER_PROGRAM);
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
