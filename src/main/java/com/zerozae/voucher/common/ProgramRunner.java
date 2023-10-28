package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xml.sax.ErrorHandler;

@Component
public class ProgramRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProgramRunner.class);
    private static final String NOT_EXIST_MENU = "존재하지 않는 메뉴입니다.";

    private final MenuHandler menuHandler;

    public ProgramRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) {
        boolean continueRun = true;
        while(continueRun){
            try{
                MenuType command = menuHandler.selectCommand("mainProgram");

                switch (command){
                    case VOUCHER -> {
                        continueRun = isVoucherMenuSelected();
                    }
                    case CUSTOMER -> {
                        continueRun = isCustomerMenuSelected();
                    }
                    case WALLET -> {
                        continueRun = isWalletMenuSelected();
                    }
                    case EXIT -> {
                        menuHandler.exit();
                        continueRun = false;
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
                MenuType command = menuHandler.selectCommand("voucherProgram");
                switch (command) {
                    case CREATE -> {
                        menuHandler.createVoucher();
                    }
                    case LIST -> {
                        menuHandler.voucherList();
                    }
                    case SEARCH -> {
                        menuHandler.findVoucherById();
                    }
                    case UPDATE -> {
                        menuHandler.updateVoucher();
                    }
                    case DELETE -> {
                        menuHandler.deleteVoucherById();
                    }
                    case DELETE_ALL -> {
                        menuHandler.deleteAllVouchers();
                    }
                    case BACK -> {
                        return true;
                    }
                    default -> {
                        logger.warn(NOT_EXIST_MENU);
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
                MenuType command = menuHandler.selectCommand("customerProgram");
                switch (command) {
                    case CREATE -> {
                        menuHandler.createCustomer();
                    }
                    case LIST -> {
                        menuHandler.customerList();
                    }
                    case SEARCH -> {
                        menuHandler.findCustomerById();
                    }
                    case UPDATE -> {
                        menuHandler.updateCustomer();
                    }
                    case DELETE -> {
                        menuHandler.deleteCustomerById();
                    }
                    case DELETE_ALL -> {
                        menuHandler.deleteAllCustomers();
                    }
                    case BLACKLIST -> {
                        menuHandler.customerBlacklist();
                    }
                    case BACK -> {
                        return true;
                    }
                    default -> {
                        logger.warn(NOT_EXIST_MENU);
                    }
                }
            }catch (ErrorMessage e) {
                logger.warn("Error Message = {}", e.getMessage());
            }
        }
    }

    private boolean isWalletMenuSelected() {
        while(true){
            try {
                MenuType command = menuHandler.selectCommand("walletProgram");
                switch (command) {
                    case ASSIGN -> {
                        menuHandler.assignVoucherToCustomer();
                    }
                    case REMOVE -> {
                        menuHandler.removeVoucherFromCustomer();
                    }
                    case VOUCHER_LIST -> {
                        menuHandler.voucherListOwnedByCustomer();
                    }
                    case OWNER -> {
                        menuHandler.findVoucherOwnerList();
                    }
                    case DELETE_ALL -> {
                        menuHandler.deleteAllWallets();
                    }
                    case BACK -> {
                        return true;
                    }
                    default -> {
                        logger.warn(NOT_EXIST_MENU);
                    }
                }
            }catch (ErrorMessage e) {
                logger.warn("Error Message ={}", e.getMessage());
            }
        }
    }
}
