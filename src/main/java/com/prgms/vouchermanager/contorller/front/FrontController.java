package com.prgms.vouchermanager.contorller.front;


import com.prgms.vouchermanager.contorller.customer.CustomerController;
import com.prgms.vouchermanager.contorller.voucher.VoucherController;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import com.prgms.vouchermanager.util.io.ConsoleOutput;
import com.prgms.vouchermanager.validation.InputValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import static com.prgms.vouchermanager.contorller.customer.CustomerMenuType.BLACK_LIST;
import static com.prgms.vouchermanager.contorller.front.FrontMenuType.*;
import static com.prgms.vouchermanager.contorller.voucher.VoucherMenuType.CREATE;
import static com.prgms.vouchermanager.contorller.voucher.VoucherMenuType.LIST;
import static com.prgms.vouchermanager.exception.ExceptionType.*;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_CUSTOMER_MENU;


@Controller
public class FrontController {

    private final static Logger logger = LoggerFactory.getLogger(FrontController.class);

    private final ConsoleInput consoleInput;

    private final ConsoleOutput consoleOutput;

    private final InputValidation inputValidation;

    private final VoucherController voucherController;

    private final CustomerController customerController;


    public FrontController(ConsoleInput consoleInput, ConsoleOutput consoleOutput, InputValidation inputValidation, VoucherController voucherController, CustomerController customerController) {

        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.inputValidation = inputValidation;
        this.voucherController = voucherController;
        this.customerController = customerController;

    }

    public void run() {

        boolean end = false;
        int menu =0;

        while (!end) {

            try {
                consoleOutput.printFrontMenu();
                menu = consoleInput.inputFrontMenu();
                if (!inputValidation.validFrontMenu(menu)) {
                    throw new RuntimeException(INVALID_FRONT_MENU.getMessage());
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                continue;
            }

            if (menu == VOUCHER.getNumber()) {
                runVoucherController();
            } else if (menu == CUSTOMER.getNumber()) {
                runCustomerContoller();
            } else {
                end = true;
            }

        }
    }
    private void runVoucherController() {

        consoleOutput.printVoucherMenu();
        int menu = 0;
        try {
            menu = consoleInput.inputVoucherMenu();

            if (!inputValidation.validVoucherMenu(menu)) {
                logger.warn(INVALID_VOUCHER_MENU.getMessage());
                throw new RuntimeException(INVALID_VOUCHER_MENU.getMessage());
            }

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
            logger.warn(e.getMessage());
            return;
        }

        if (menu == CREATE.getNumber()) {
            voucherController.create();
        } else if (menu == LIST.getNumber()) {
            voucherController.getList();
        }
    }

    private void runCustomerContoller() {

        consoleOutput.printCustomerMenu();
        int menu = 0;
        try {
            menu = consoleInput.inputCustomerMenu();

            if (!inputValidation.validCustomerMenu(menu)) {
                logger.warn(INVALID_CUSTOMER_MENU.getMessage());
                throw new RuntimeException(INVALID_CUSTOMER_MENU.getMessage());
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            logger.warn(e.getMessage());
            return;
        }
        if (menu == BLACK_LIST.getNumber()) {
            customerController.getBlackList();
        }
    }
}
