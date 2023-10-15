package com.prgrms.springbasic.domain.customer.controller;

import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.service.CustomerService;
import com.prgrms.springbasic.io.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    Console console = new Console();
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerResponse> findAllBlackLists() {
        List<CustomerResponse> customers = customerService.findAllBlackList();
        console.printCustomers(customers);
        return customers;
    }
}
