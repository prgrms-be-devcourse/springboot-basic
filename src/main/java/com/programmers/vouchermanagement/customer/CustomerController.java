package com.programmers.vouchermanagement.customer;

import java.util.List;

public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> readBlacklist() {
        return customerService.readBlacklist();
    }
}
