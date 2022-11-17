package com.program.commandLine;

import com.program.commandLine.controller.CustomerController;
import com.program.commandLine.controller.VoucherController;
import com.program.commandLine.io.Console;
import com.program.commandLine.customer.Customer;
import com.program.commandLine.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component(value = "systemController")
public class SystemController implements Runnable {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    final Logger logger = LoggerFactory.getLogger(CommandLineProgramApplication.class);

    private enum MenuType {
        EXIT, CREATE, LIST, BLACKLIST
    }


    public SystemController(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run() {
        Boolean systemFlag = true;
        while (systemFlag) {
            console.menuView();
            String choseMenu = console.input();

            try {
                MenuType menuType = MenuType.valueOf(choseMenu.toUpperCase());
                switch (menuType) {
                    case LIST -> viewVoucherList();
                    case CREATE -> createVoucher();
                    case BLACKLIST -> viewBlackListCustomer();
                    case EXIT -> {
                        console.successMessageView("End the program. See you.");
                        systemFlag = false;
                    }
                }
            } catch (Exception e) {
                logger.warn("컨트롤러 run 메소드에서 에러 처리 :" + e.getMessage());
                console.errorMessageView(e.getMessage());
            }

        }
    }

    private void createVoucher() {
        String voucherType = console.input("What type getType voucher to create?   1.Fixed Amount   2.percent discount : ");
        String discount = console.input("Enter discount amount or percentage : ");

        Voucher voucher =  voucherController.createVoucher(voucherType, Integer.parseInt(discount));
        if (voucher != null){ // option?
            console.successMessageView("Success create voucher!");
        }
    }

    private void viewVoucherList() {
        console.allVoucherView(voucherController.getVoucherList());
    }

    private void viewBlackListCustomer() {
        List<Customer> blackList = customerController.getCustomerBlackList();
        console.customerBlackListView(blackList);
    }
}
