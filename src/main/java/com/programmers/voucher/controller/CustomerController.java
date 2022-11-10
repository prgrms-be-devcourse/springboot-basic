package com.programmers.voucher.controller;

import com.programmers.voucher.io.Console;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {
    private final Console console;
    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void getBlacklist() {
        List<Customer> blacks = customerService.findAllBlack();
        console.printBlacks(blacks);
    }
}
