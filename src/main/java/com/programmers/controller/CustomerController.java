package com.programmers.controller;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.dto.CustomerCreateRequestDto;
import com.programmers.domain.customer.dto.CustomerResponseDto;
import com.programmers.io.Console;
import com.programmers.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final Console console;
    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public Customer createCustomer() {
        console.printCustomerNameInput();
        String customerName = console.readInput();

        Customer customer = makeCustomer(customerName);

        console.printCustomerCreated();
        log.info("The customer has been created.");

        return customer;
    }

    public Customer makeCustomer(String customerName) {
        CustomerCreateRequestDto customerCreateRequestDto = new CustomerCreateRequestDto(customerName);
        CustomerResponseDto customerResponseDto = customerService.save(customerCreateRequestDto);

        return new Customer(customerResponseDto.id(), customerResponseDto.name());
    }
}
