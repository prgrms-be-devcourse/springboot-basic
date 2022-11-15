package com.program.commandLine.controller;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component(value = "customerController")
public class CustomerController {

    private final CustomerService customerService;
    final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> getCustomerBlackList() {
        try {
            List<Customer> blackList = customerService.getBlackListCustomers();
            return blackList;
        } catch (Exception e) {
            logger.warn("black list 파일 열기 실패");
            throw new RuntimeException("! Failed to open blacklist file");
        }
    }

}
