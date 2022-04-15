package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.VoucherType;
import org.prgrms.springbootbasic.exception.ServiceException;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.view.ConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final ConsoleView consoleView;
    private final CustomerService customerService;

    public VoucherController(VoucherService voucherService, ConsoleView consoleView,
        CustomerService customerService) {
        this.voucherService = voucherService;
        this.consoleView = consoleView;
        this.customerService = customerService;
    }

    public void run() {
        while (process()) {
        }
    }

    public boolean process() {
        logger.info("process() called");

        consoleView.printMenu();
        Menu menu = consoleView.inputMenu();

        try {
            return menu.apply(this);
        } catch (ServiceException exception) {
            logger.info("Got service exception");

            consoleView.printError(exception.getMessage());
            return true;
        } catch (Exception exception) {
            logger.error("Got system exception", exception);
            return false;
        }
    }

    public void createVoucher() {
        logger.info("createVoucher() called");

        VoucherType voucherType = consoleView.selectVoucherType();

        if (voucherType.isFixed()) {
            int amount = consoleView.selectAmount();
            voucherService.createFixedAmountVoucher(amount);
        }

        if (voucherType.isPercent()) {
            int percent = consoleView.selectPercent();
            voucherService.createPercentAmountVoucher(percent);
        }
    }

    public void printList() {
        logger.info("printList() called");

        consoleView.printList(voucherService.findAll());
    }

    public void printBlackList() {
        logger.info("printBlackList() called");

        consoleView.printCustomerBlackList();
    }

    public void createCustomer() {
        logger.info("createCustomer() called");

        var name = consoleView.selectName();
        var email = consoleView.selectEmail();
        customerService.createCustomer(name, email);
    }

    public void printAllCustomers() {
        logger.info("printAllCustomers() called");

        consoleView.printAllCustomers(customerService.findAllCustomers());
    }

    public void assignVoucher() {
        logger.info("assignVoucher() called");

        var voucherId = consoleView.selectVoucherId();
        var customerId = consoleView.selectCustomerId();
        voucherService.assignVoucherToCustomer(voucherId, customerId);
    }
}
