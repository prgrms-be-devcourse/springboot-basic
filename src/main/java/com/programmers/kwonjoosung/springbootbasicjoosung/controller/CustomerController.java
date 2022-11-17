package com.programmers.kwonjoosung.springbootbasicjoosung.controller;


import com.programmers.kwonjoosung.springbootbasicjoosung.service.CustomerService;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

}
