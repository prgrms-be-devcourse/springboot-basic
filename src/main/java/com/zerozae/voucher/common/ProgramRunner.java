package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ExceptionHandler;
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
            try{
                MenuType command = menuHandler.showAndSelectCommand();

                boolean continueRun = true;
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
                if(!continueRun) break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isVoucherMenuSelected() {
        while(true){
            try{
                MenuType command = menuHandler.selectedVoucherProgram();
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
                MenuType command = menuHandler.selectedCustomerProgram();
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
