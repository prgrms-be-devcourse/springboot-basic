package com.prgms.vouchermanager.contorller.front;


import com.prgms.vouchermanager.contorller.customer.CustomerController;
import com.prgms.vouchermanager.contorller.customer.CustomerMenuType;
import com.prgms.vouchermanager.contorller.voucher.VoucherController;
import com.prgms.vouchermanager.contorller.voucher.VoucherMenuType;
import com.prgms.vouchermanager.contorller.wallet.WalletController;
import com.prgms.vouchermanager.contorller.wallet.WalletMenuType;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import com.prgms.vouchermanager.util.io.ConsoleOutput;
import com.prgms.vouchermanager.validation.InputValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_FRONT_MENU;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_WALLET_MENU;

@Slf4j
@Controller
public class FrontController {

    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    private final InputValidation inputValidation;

    public FrontController(ConsoleInput consoleInput, ConsoleOutput consoleOutput, VoucherController voucherController, CustomerController customerController, WalletController walletController, InputValidation inputValidation) {

        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
        this.inputValidation = inputValidation;
    }

    public void run() {

        boolean end = false;
        int menu = 0;

        while (!end) {

            try {
                consoleOutput.printFrontMenu();
                menu = consoleInput.inputFrontMenu();
                if (!inputValidation.validFrontMenu(menu)) {
                    throw new RuntimeException(INVALID_FRONT_MENU.getMessage());
                }
                switch (FrontMenuType.fromValue(menu)) {
                    case VOUCHER -> runVoucherController();
                    case CUSTOMER -> runCustomerContoller();
                    case WALLET -> runWalletController();
                    case END -> end = true;
                }

            } catch (RuntimeException e) {
                log.warn(e.getMessage());
            }

        }
    }

    private void runVoucherController() {

        consoleOutput.printVoucherMenu();
        int menu;
        try {
            menu = consoleInput.inputVoucherMenu();

            switch (VoucherMenuType.fromValue(menu)) {
                case CREATE -> voucherController.create();
                case UPDATE -> voucherController.update();
                case LIST -> voucherController.getList();
                case ONE -> voucherController.findById();
                case DELETE_ONE -> voucherController.deleteById();
                case DELETE_ALL -> voucherController.deleteAll();

            }

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }


    }

    private void runCustomerContoller() {

        consoleOutput.printCustomerMenu();
        int menu = 0;
        try {
            menu = consoleInput.inputCustomerMenu();

            switch (CustomerMenuType.fromValue(menu)) {
                case CREATE -> customerController.create();
                case UPDATE -> customerController.update();
                case FIND_ALL -> customerController.getList();
                case FIND_ONE -> customerController.findById();
                case DELETE_ONE -> customerController.deleteById();
                case DELETE_ALL -> customerController.deleteAll();
                case BLACK_LIST -> customerController.findBlackList();

            }
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }


    }

    private void runWalletController() {

        consoleOutput.printWalletMenu();
        int menu = 0;
        try {
            menu = consoleInput.inputVoucherMenu();

            if (!inputValidation.validWalletMenu(menu)) {
                throw new RuntimeException(INVALID_WALLET_MENU.getMessage());
            }

            switch (WalletMenuType.fromValue(menu)) {

                case CREATE -> walletController.create();
                case FIND_BY_CUSTOMER_ID -> walletController.findByCustomerId();
                case FIND_BY_VOUCHER_ID -> walletController.findByVoucherId();
                case DELETE_BY_CUSTOMER_ID -> walletController.deleteByCustomerId();

            }

        } catch (RuntimeException e) {

            log.warn(e.getMessage());
        }
    }
}
