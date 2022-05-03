package org.prgrms.springbootbasic.controller.console;

import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Email;
import org.prgrms.springbootbasic.entity.customer.Name;
import org.prgrms.springbootbasic.exception.ServiceException;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Controller
@Profile("console")
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;
    private final View view;
    private final CustomerService customerService;

    public VoucherController(VoucherService voucherService, View view,
        CustomerService customerService) {
        this.voucherService = voucherService;
        this.view = view;
        this.customerService = customerService;
    }

    public void run() {
        boolean isProcessed;
        do {
            isProcessed = process();
        } while (isProcessed);
    }

    public boolean process() {
        logger.info("process() called");

        view.printMenu();

        try {
            Menu menu = view.inputMenu();
            return menu.apply(this);
        } catch (ServiceException exception) {
            logger.info("Got service exception");

            view.printError(exception.getMessage());
            return true;
        } catch (Exception exception) {
            logger.error("Got system exception", exception);
            return false;
        }
    }

    public void createVoucher() {
        logger.info("createVoucher() called");

        VoucherType voucherType = view.selectVoucherType();

        if (voucherType.isFixed()) {
            int amount = view.selectAmount();
            voucherService.createVoucher(voucherType, amount, 0);
        }

        if (voucherType.isPercent()) {
            int percent = view.selectPercent();
            voucherService.createVoucher(voucherType, 0, percent);
        }
    }

    public void printList() {
        logger.info("printList() called");

        view.printList((voucherService.findAll()));
    }

    public void printBlackList() {
        logger.info("printBlackList() called");

        view.printCustomerBlackList();
    }

    public void createCustomer() {
        logger.info("createCustomer() called");

        var name = view.selectName();
        var email = view.selectEmail();
        customerService.createCustomer(new Name(name), new Email(email));
    }

    public void printAllCustomers() {
        logger.info("printAllCustomers() called");

        view.printAllCustomers(customerService.findAllCustomers());
    }

    public void assignVoucher() {
        logger.info("assignVoucher() called");

        var voucherId = view.selectVoucherId();
        var customerId = view.selectCustomerId();
        voucherService.assignVoucherToCustomer(voucherId, customerId);
    }

    public void listCustomerVoucher() {
        logger.info("listCustomerVoucher() called");

        var customerId = view.selectCustomerId();
        view.printCustomerVouchers(voucherService.findCustomerVoucher(customerId));
    }

    public void deleteCustomerVoucher() {
        logger.info("deleteCustomerVoucher() called");

        var customerId = view.selectCustomerId();
        var voucherId = view.selectVoucherId();
        customerService.deleteVoucher(customerId, voucherId);
    }

    public void listCustomerHavingSpecificVoucherType() {
        logger.info("listCustomerHavingSpecificVoucherType() called");

        var voucherType = view.selectVoucherType();
        view.printAllCustomers(
            customerService.findCustomerHavingSpecificVoucherType(voucherType));
    }
}
