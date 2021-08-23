package com.prgms.kdtspringorder.adapter.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.prgms.kdtspringorder.application.CustomerService;
import com.prgms.kdtspringorder.domain.model.customer.Customer;
import com.prgms.kdtspringorder.ui.Printer;

@Controller
public class CustomerController {
    private final Printer printer = new Printer();
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void findAllBlacklist() {
        Map<UUID, Customer> blackList = customerService.findAllBlackList();
        printer.printBlacklist(blackList);
    }
}
