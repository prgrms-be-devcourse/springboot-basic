package com.devcourse.springbootbasic.application;

import com.devcourse.springbootbasic.application.customer.controller.CustomerController;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.io.ConsoleManager;
import com.devcourse.springbootbasic.application.global.model.ListMenu;
import com.devcourse.springbootbasic.application.global.model.Menu;
import com.devcourse.springbootbasic.application.voucher.VoucherConverter;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsoleApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);

    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ConsoleApplication(
            ConsoleManager consoleManager,
            VoucherController voucherController,
            CustomerController customerController
    ) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Menu menu = consoleManager.consoleMenu();
                if (branchByMenu(menu)) {
                    break;
                }
            } catch (InvalidDataException exception) {
                logger.error(exception.getMessage(), exception.getCause());
                consoleManager.consoleError(exception);
            }
        }
    }

    private boolean branchByMenu(Menu menu) {
        switch (menu) {
            case EXIT -> {
                consoleManager.consoleClosePlatform();
                return true;
            }
            case CREATE -> {
                VoucherConverter.convertDtoToVoucher(consoleManager.getVoucherDto(), UUID.randomUUID());
            }
            case LIST -> {
                var listMenu = consoleManager.consoleListMenu();
                branchByListMenu(listMenu);
            }
        }
        return false;
    }

    private void branchByListMenu(ListMenu listMenu) {
        var list = switch (listMenu) {
            case VOUCHER_LIST -> voucherController.getAllVouchers();
            case BLACK_CUSTOMER_LIST -> customerController.findBlackCustomers();
        };
        consoleManager.printList(
                listMenu,
                list.stream()
                        .map(Object::toString)
                        .toList()
        );
    }

}
