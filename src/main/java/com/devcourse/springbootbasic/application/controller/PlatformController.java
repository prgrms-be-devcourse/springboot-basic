package com.devcourse.springbootbasic.application.controller;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.dto.ListMenu;
import com.devcourse.springbootbasic.application.dto.Menu;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.io.ConsoleManager;
import com.devcourse.springbootbasic.application.service.CustomerService;
import com.devcourse.springbootbasic.application.service.VoucherService;
import com.devcourse.springbootbasic.application.converter.CustomerConverter;
import com.devcourse.springbootbasic.application.converter.VoucherConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class PlatformController implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);
    private boolean isRunning;

    private final ConsoleManager consoleManager;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public PlatformController(ConsoleManager consoleManager, VoucherService voucherService, CustomerService customerService) {
        this.consoleManager = consoleManager;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Menu menu = consoleManager.consoleMenu();
                branchByMenu(menu);
            } catch (InvalidDataException exception) {
                logger.error(exception.getMessage(), exception.getCause());
                consoleManager.consoleError(exception);
            }
        }
    }

    private void branchByMenu(Menu menu) {
        switch (menu) {
            case EXIT -> {
                isRunning = false;
                consoleManager.consoleClosePlatform();
            }
            case CREATE -> {
                Voucher voucher = VoucherConverter.convertDtoToVoucher(
                        consoleManager.getVoucherDto(), UUID.randomUUID()
                );
                voucherService.createVoucher(voucher);
            }
            case LIST -> {
                var listMenu = consoleManager.consoleListMenu();
                branchByListMenu(listMenu);
            }
        }
    }

    private void branchByListMenu(ListMenu listMenu) {
        var list = switch (listMenu) {
            case VOUCHER_LIST -> VoucherConverter.convertToStringList(voucherService.getVouchers());
            case BLACK_CUSTOMER_LIST -> CustomerConverter.convertToStringList(customerService.getBlackCustomers());
        };
        consoleManager.printList(listMenu, list);
    }

}
