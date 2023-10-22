package com.pgms.part1.domain.customer.controller;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.service.CustomerService;
import com.pgms.part1.view.ConsoleView;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final ConsoleView consoleView;

    public CustomerController(CustomerService customerService, ConsoleView consoleView) {
        this.customerService = customerService;
        this.consoleView = consoleView;
    }

    public void listBlockedCustomers(){
        List<CustomerResponseDto> customerResponseDtos = customerService.listBlockedCustomers();
        consoleView.listBlockedCustomers(customerResponseDtos);
    }
}
