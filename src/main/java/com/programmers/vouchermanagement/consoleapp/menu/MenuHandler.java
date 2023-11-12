package com.programmers.vouchermanagement.consoleapp.menu;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerConsoleController;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.voucher.controller.VoucherConsoleController;
import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("console")
@Component
public class MenuHandler {
    private static final Logger logger = LoggerFactory.getLogger(MenuHandler.class);

    //messages
    private static final String INCORRECT_MESSAGE =
            "This menu is not executable.";
    //---

    private final ConsoleManager consoleManager;
    private final VoucherConsoleController voucherController;
    private final CustomerConsoleController customerController;

    public MenuHandler(ConsoleManager consoleManager, VoucherConsoleController voucherController, CustomerConsoleController customerController) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public boolean handleMenu() {
        Menu menu = selectMenu();

        try {
            executeMenu(menu);
        } catch (RuntimeException e) {
            consoleManager.printException(e);
        }

        return isValidMenu(menu);
    }

    private Menu selectMenu() {
        return consoleManager.selectMenu();
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
            case CREATE -> {
                CreateVoucherRequest createVoucherRequest = consoleManager.instructCreate();
                VoucherResponse voucher = voucherController.create(createVoucherRequest);
                consoleManager.printCreateResult(voucher);
            }
            case LIST -> {
                List<VoucherResponse> vouchers = voucherController.readAll();
                consoleManager.printReadAllVouchers(vouchers);
            }
            case BLACKLIST -> {
                List<CustomerResponse> blackCustomers = customerController.readAllBlackCustomer();
                consoleManager.printReadBlacklist(blackCustomers);
            }
        }
    }
}
