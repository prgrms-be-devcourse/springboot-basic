package com.prgms.kdtspringorder.adapter.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.prgms.kdtspringorder.application.CustomerService;
import com.prgms.kdtspringorder.domain.model.customer.Customer;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Map<UUID, Customer> findAllBlacklist() {
        return customerService.findAllBlackList();
    }
}
