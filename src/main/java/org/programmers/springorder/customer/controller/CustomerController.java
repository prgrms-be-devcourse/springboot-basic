package org.programmers.springorder.customer.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
    private final Console console;

    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void printBlackList(){
        console.showBlackList(customerService.getBlackList());
    }
}
