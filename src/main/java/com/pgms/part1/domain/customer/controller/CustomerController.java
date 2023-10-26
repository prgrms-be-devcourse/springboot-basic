package com.pgms.part1.domain.customer.controller;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.service.CustomerService;
import com.pgms.part1.view.CustomerConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {
    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final CustomerConsoleView customerConsoleView;

    public CustomerController(CustomerService customerService, CustomerConsoleView customerConsoleView) {
        this.customerService = customerService;
        this.customerConsoleView = customerConsoleView;
    }

    public void listBlockedCustomers(){
        List<CustomerResponseDto> customerResponseDtos = customerService.listBlockedCustomers();
        customerConsoleView.listBlockedCustomers(customerResponseDtos);
    }

    public void getMenu() {
        String command = customerConsoleView.getMenu();

        switch (command) {
            case "create" -> createCustomer();
            case "list" -> listCustomers();
            case "blacklist" -> listBlockedCustomers();
            case "block" -> updateCustomerBlocked();
            case "exit" -> {return;}
            default -> {
                customerConsoleView.error(new RuntimeException("Please Enter Again!!"));
                log.warn("Invalid Menu Command Input");
                getMenu();
            }
        }
        getMenu();
    }

    private void updateCustomerBlocked() {
        Long id = customerConsoleView.updateCustomerBlocked();

        customerService.isAvailableCustomer(id);

        customerService.updateCustomerBlocked(id);
    }

    private void listCustomers() {
        List<CustomerResponseDto> customerResponseDtos = customerService.listCustomers();
        customerConsoleView.listCustomers(customerResponseDtos);
    }

    private void createCustomer() {
        CustomerCreateRequestDto customerCreateRequestDto = customerConsoleView.createCustomer();
        customerService.addCustomer(customerCreateRequestDto);
    }
}
