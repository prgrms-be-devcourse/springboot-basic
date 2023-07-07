package com.devcourse.springbootbasic.application;

import com.devcourse.springbootbasic.application.controller.PlatformController;
import com.devcourse.springbootbasic.application.customer.CustomerConverter;
import com.devcourse.springbootbasic.application.voucher.VoucherConverter;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.global.model.ListMenu;
import com.devcourse.springbootbasic.application.global.model.Menu;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.io.ConsoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsoleApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);

    private final ConsoleManager consoleManager;
    private final PlatformController platformController;

    public ConsoleApplication(ConsoleManager consoleManager, PlatformController platformController) {
        this.consoleManager = consoleManager;
        this.platformController = platformController;
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
                Voucher voucher = VoucherConverter.convertDtoToVoucher(
                        consoleManager.getVoucherDto(), UUID.randomUUID()
                );
                platformController.createVoucher(voucher);
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
            case VOUCHER_LIST -> VoucherConverter.convertToStringList(platformController.getVouchers());
            case BLACK_CUSTOMER_LIST -> CustomerConverter.convertToStringList(platformController.getBlackCustomers());
        };
        consoleManager.printList(listMenu, list);
    }
}
