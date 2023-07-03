package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.util.ConsoleMessages;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

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

        console.printCustomers(customers);
    }

    public void createCustomer() {
        CustomerCreateRequest request = console.inputCustomerCreateInfo();

        UUID customerId = customerService.createCustomer(request.getEmail(), request.getName());

        String consoleMessage = String.format(ConsoleMessages.CREATED_NEW_CUSTOMER, customerId);
        console.print(consoleMessage);
    }
}
