package com.program.commandLine.controller;

import com.program.commandLine.CommandLineProgramApplication;
import com.program.commandLine.io.Console;
import com.program.commandLine.io.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component(value = "systemController")
public class SystemController implements Runnable {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final VoucherWalletController voucherWalletController;

    final Logger logger = LoggerFactory.getLogger(CommandLineProgramApplication.class);

    public SystemController(Console console, VoucherController voucherController, CustomerController customerController, VoucherWalletController voucherWalletController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.voucherWalletController = voucherWalletController;
    }

    private enum CustomerMenuType {
        CREATE, SEARCH, BLACKLIST, DELETE
    }

    private enum VoucherMenuType {
        CREATE, ALLOCATE, RETRIEVE, LIST, CUSTOMER
    }

    @Override
    public void run() {
        Boolean systemFlag = true;
        while (systemFlag) {
            console.menuView(MenuType.MAIN);
            String choseMenu = console.input();
            try {
                MenuType menuType = MenuType.valueOf(choseMenu.toUpperCase());
                switch (menuType) {
                    case CUSTOMER -> customerServiceRun();
                    case VOUCHER -> voucherServiceRun();
                    case EXIT -> {
                        console.successMessageView("  프로그램 종료  ");
                        systemFlag = false;
                    }
                }
            } catch (Exception e) {
                logger.warn("컨트롤러 run 메소드에서 에러 처리 :" + e.getMessage());
                console.errorMessageView(e.getMessage());
            }

        }
    }

    private void customerServiceRun() {
        console.menuView(MenuType.CUSTOMER);
        String choseMenu = console.input();
        CustomerMenuType customerMenuType = CustomerMenuType.valueOf(choseMenu.toUpperCase());
        switch (customerMenuType) {
            case CREATE -> customerController.createCustomer();
            case SEARCH -> customerController.searchCustomerByName();
            case BLACKLIST -> customerController.LookupCustomerBlackList();
            case DELETE -> customerController.deleteAllCustomer();
        }

    }

    private void voucherServiceRun() {
        console.menuView(MenuType.VOUCHER);
        String choseMenu = console.input();
        VoucherMenuType voucherMenuType = VoucherMenuType.valueOf(choseMenu.toUpperCase());
        switch (voucherMenuType) {
            case CREATE -> voucherController.createVoucher();
            case ALLOCATE -> voucherWalletController.assignCustomer();
            case RETRIEVE -> voucherWalletController.retrieveVoucher();
            case LIST -> voucherController.lookupAllVoucherList();
            case CUSTOMER -> voucherWalletController.lookupCustomerWithVoucher();
        }
    }

}
