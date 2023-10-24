package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProgramRunner.class);
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

                switch (command) {
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
                    default -> {
                        logger.warn("존재하지 않는 메뉴입니다.");
                    }
                }
            }catch (ErrorMessage e) {
                logger.warn("Error Message = {}", e.getMessage());
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
                    case OWNER -> {
                        menuHandler.findVoucherOwner();
                    }
                    case DELETE -> {
                        menuHandler.removeVoucherById();
                    }
                    case DELETE_ALL -> {
                        menuHandler.removeAllVouchers();
                    }
                    case SEARCH -> {
                        menuHandler.findVoucherById();
                    }
                    case UPDATE -> {
                        menuHandler.updateVoucher();
                    }
                    case BACK -> {
                        return true;
                    }
                    default -> {
                        logger.warn("존재하지 않는 메뉴입니다.");
                    }
                }
            }catch (ErrorMessage e) {
                logger.warn("Error Message = {}", e.getMessage());
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
                    case REGISTER -> {
                        menuHandler.registerVoucher();
                    }
                    case REMOVE -> {
                        menuHandler.removeVoucherFromCustomer();
                    }
                    case VOUCHER_LIST ->  {
                        menuHandler.customerVoucherList();
                    }
                    case DELETE -> {
                        menuHandler.removeCustomerById();
                    }
                    case DELETE_ALL -> {
                        menuHandler.removeAllCustomers();
                    }
                    case SEARCH -> {
                        menuHandler.findCustomerById();
                    }
                    case UPDATE -> {
                        menuHandler.updateCustomer();
                    }
                    case BACK -> {
                        return true;
                    }
                    default -> {
                        logger.error("존재하지 않는 메뉴입니다.");
                    }
                }
            }catch (ErrorMessage e) {
                logger.warn("Error Message = {}", e.getMessage());
            }
        }
    }
}