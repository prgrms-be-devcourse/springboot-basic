package com.programmers.vouchermanagement.customer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.service.CustomerService;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final ConsoleManager consoleManager;

    public CustomerController(CustomerService customerService, ConsoleManager consoleManager) {
        this.customerService = customerService;
        this.consoleManager = consoleManager;
    }

    public void readBlacklist() {
        List<CustomerResponse> blacklist = customerService.readBlacklist();
        consoleManager.printReadBlacklist(blacklist);
    }

    public void create(String name) {
        CustomerResponse customerResponse = customerService.create(name);
        consoleManager.printSaveCustomerResult(customerResponse);
    }
}
