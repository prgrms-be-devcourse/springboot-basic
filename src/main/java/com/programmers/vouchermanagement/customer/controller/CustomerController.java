package com.programmers.vouchermanagement.customer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.service.CustomerService;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> readAllBlackCustomer() {
        return customerService.readAllBlackCustomer();
    }
}
