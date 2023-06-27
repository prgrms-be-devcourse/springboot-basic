package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> findBlacklistCustomers() {
        return customerService.findBlacklistCustomers();
    }
}
