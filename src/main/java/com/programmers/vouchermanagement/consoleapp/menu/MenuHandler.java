package com.programmers.vouchermanagement.consoleapp.menu;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerController;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.controller.VoucherController;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuHandler {
    private static final Logger logger = LoggerFactory.getLogger(MenuHandler.class);

    //messages
    private static final String INCORRECT_MESSAGE =
            "This menu is not executable.";
    //---

    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(ConsoleManager consoleManager, VoucherController voucherController, CustomerController customerController) {
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
            throw new IllegalArgumentException(INCORRECT_MESSAGE);
        }

        return true;
    }

    private void executeMenu(Menu menu) {
        switch (menu) {
            case EXIT -> consoleManager.printExit();
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
            case CREATE -> {
                CreateVoucherRequest createVoucherRequest = consoleManager.instructCreate();
                VoucherResponse voucherResponse = voucherController.create(createVoucherRequest);
                consoleManager.printCreateResult(voucherResponse);
            }
            case LIST -> {
                List<VoucherResponse> voucherResponses = voucherController.readAllVouchers();
                consoleManager.printReadAllVouchers(voucherResponses);
            }
            //TODO: customerDTO
            case BLACKLIST -> {
                List<Customer> customerResponses = customerController.readBlacklist();
                consoleManager.printReadBlacklist(customerResponses);
            }
        }
    }
}
