package com.prgms.vouchermanager.contorller.front;


import com.prgms.vouchermanager.contorller.customer.CustomerController;
import com.prgms.vouchermanager.contorller.voucher.VoucherController;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import static com.prgms.vouchermanager.contorller.front.FrontMenuType.*;


@Controller
public class FrontController {

    private final static Logger logger = LoggerFactory.getLogger(FrontController.class);

    private final ConsoleInput consoleInput;

    private final VoucherController voucherController;

    private final CustomerController customerController;


    public FrontController(ConsoleInput consoleInput, VoucherController voucherController, CustomerController customerController) {

        this.consoleInput = consoleInput;
        this.voucherController = voucherController;
        this.customerController = customerController;

    }

    public void run() {

        boolean end = false;
        int menu;

        while (!end) {

            try {
                menu = consoleInput.inputFrontMenu();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                continue;
            }

            if (menu == VOUCHER.getNumber()) {
                voucherController.run();
            } else if (menu == CUSTOMER.getNumber()) {
                customerController.run();
            } else {
                end = true;
            }

        }
    }
}
