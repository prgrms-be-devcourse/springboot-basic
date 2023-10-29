package com.pgms.part1.domain.common.controller;

import com.pgms.part1.domain.customer.controller.CustomerController;
import com.pgms.part1.domain.voucher.controller.VoucherController;
import com.pgms.part1.domain.wallet.controller.WalletController;
import com.pgms.part1.view.CommonConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CommonController {

    private final Logger log = LoggerFactory.getLogger(CommonController.class);
    private final CommonConsoleView commonConsoleView;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public CommonController(CommonConsoleView commonConsoleView, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.commonConsoleView = commonConsoleView;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    public void getMenu() {
        String command = commonConsoleView.getMenu();

        switch (command) {
            case "customer" -> customerController.getMenu();
            case "voucher" -> voucherController.getMenu();
            case "wallet" -> walletController.getMenu();
            case "exit" -> exit();
            default -> {
                commonConsoleView.error(new RuntimeException("Please Enter Again!!"));
                log.warn("Invalid Menu Command Input");
            }
        }
        getMenu();
    }

    public void exit(){
        System.exit(0);
    }
}
