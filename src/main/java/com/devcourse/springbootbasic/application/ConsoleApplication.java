package com.devcourse.springbootbasic.application;

import com.devcourse.springbootbasic.application.customer.controller.CustomerController;
import com.devcourse.springbootbasic.application.customer.controller.CustomerDto;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.io.ConsoleManager;
import com.devcourse.springbootbasic.application.global.model.CommandMenu;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherController;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);

    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ConsoleApplication(ConsoleManager consoleManager, VoucherController voucherController, CustomerController customerController) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                CommandMenu menu = consoleManager.consoleCommandMenu();
                if (branchByMenu(menu)) {
                    break;
                }
            } catch (InvalidDataException exception) {
                logger.error(exception.getMessage(), exception.getCause());
                consoleManager.consoleError(exception);
            }
        }
    }

    private boolean branchByMenu(CommandMenu menu) {
        switch (menu) {
            case EXIT -> {
                consoleManager.consoleClosePlatform();
                return true;
            }
            case REGISTER_CUSTOMER -> {
                CustomerDto customer = customerController.registerCustomer(consoleManager.consoleCustomerDto());
                consoleManager.printCustomerDto(customer);
            }
            case UNREGISTER_CUSTOMER -> {
                CustomerDto customer = customerController.unregisterCustomerById(consoleManager.consoleId());
                consoleManager.printCustomerDto(customer);
            }
            case LIST_ALL_CUSTOMERS -> {
                List<CustomerDto> customers = customerController.customerList();
                consoleManager.printCustomerList(customers);
            }
            case REGISTER_VOUCHER_TO_CUSTOMER -> {
                VoucherDto voucher = voucherController.registerVoucher(consoleManager.consoleVoucherDto());
                consoleManager.printVoucherDto(voucher);
            }
            case UNREGISTER_VOUCHER_FROM_CUSTOMER -> {
                VoucherDto voucher = voucherController.unregisterVoucherByCustomerIdAndVoucherId(consoleManager.consoleId(), consoleManager.consoleId());
                consoleManager.printVoucherDto(voucher);
            }
            case LIST_VOUCHERS_OF_CUSTOMER -> {
                List<VoucherDto> vouchers = voucherController.voucherListOfCustomer(consoleManager.consoleId());
                consoleManager.printVoucherList(vouchers);
            }
            case LIST_BLACK_CUSTOMERS -> {
                List<CustomerDto> blackCustomers = customerController.blackCustomerList();
                consoleManager.printCustomerList(blackCustomers);
            }
        }
        return false;
    }

}
