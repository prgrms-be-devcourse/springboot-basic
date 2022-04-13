package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.VoucherType;
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
        logger.info("VoucherController.process() called");

        consoleView.printMenu();
        Menu menu = consoleView.inputMenu();

        try {
            return menu.apply(this);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public void createVoucher() {
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
        consoleView.printList(voucherService.findAll());
    }

    public void printBlackList() {
        consoleView.printCustomerBlackList();
    }

    public void createCustomer() {
        var name = consoleView.selectName();
        var email = consoleView.selectEmail();
        customerService.createCustomer(name, email);
    }

    public void printAllCustomers() {
        consoleView.printAllCustomers(customerService.findAllCustomers());
    }
}
