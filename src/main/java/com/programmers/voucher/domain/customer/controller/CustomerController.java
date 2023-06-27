package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.global.io.Console;
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

    public void findBlacklistCustomers() {
        List<Customer> customers = customerService.findBlacklistCustomers();
        String customerInfos = customers.stream()
                .map(Customer::fullInfoString)
                .reduce("", (a, b) -> a + "\n" + b);

        console.print(customerInfos);
    }
}
