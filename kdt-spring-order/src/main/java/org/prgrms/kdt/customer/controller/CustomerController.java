package org.prgrms.kdt.customer.controller;

import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.io.Console;

public class CustomerController {

    private final Console console = new Console();
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        console.printBlackList(customerService.getCustomerList());
    }
}
