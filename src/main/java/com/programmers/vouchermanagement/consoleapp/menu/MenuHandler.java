package com.programmers.vouchermanagement.consoleapp.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerController;
import com.programmers.vouchermanagement.voucher.controller.VoucherController;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;

@Component
public class MenuHandler {
    private static final Logger logger = LoggerFactory.getLogger(MenuHandler.class);

    //messages
    private static final String INCORRECT_MESSAGE = "This menu is not executable.";

    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(ConsoleManager consoleManager, VoucherController voucherController, CustomerController customerController) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public boolean handleMenu() {
        Menu menu = consoleManager.selectMenu();

        try {
            executeMenu(menu);
        } catch (RuntimeException e) {
            consoleManager.printException(e);
        }

        return isValidMenu(menu);
    }

    private boolean isValidMenu(Menu menu) {
        if (menu.isExit()) {
            return false;
        }

        if (menu.isIncorrect()) {
            logger.error(INCORRECT_MESSAGE);
        }

        return true;
    }

    private void executeMenu(Menu menu) {
        switch (menu) {
            case EXIT -> consoleManager.printExit();
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
            case VOUCHER -> executeVoucherMenu();
            case CUSTOMER -> executeCustomerMenu();
        }
    }

    private void executeVoucherMenu() {
        VoucherMenu voucherMenu = consoleManager.selectVoucherMenu();
        switch (voucherMenu) {
            case CREATE -> {
                CreateVoucherRequest createVoucherRequest = consoleManager.instructCreateVoucher();
                voucherController.create(createVoucherRequest);
            }
            case LIST -> voucherController.readAllVouchers();
            case SEARCH -> {
                return;
            }
            case UPDATE -> {
                return;
            }
            case DELETE -> {
                return;
            }
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
        }
    }

    private void executeCustomerMenu() {
        CustomerMenu customerMenu = consoleManager.selectCustomerMenu();
        switch (customerMenu) {
            case CREATE -> {
                String name = consoleManager.instructCreateCustomer();
            }
            case LIST -> {
                return;
            }
            case SEARCH -> {
                return;
            }
            case UPDATE -> {
                return;
            }
            case BLACKLIST -> customerController.readBlacklist();
            case DELETE -> {
                return;
            }
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
        }
    }
}
