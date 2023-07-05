package com.programmers.controller;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.dto.CustomerCreateRequestDto;
import com.programmers.domain.customer.dto.CustomerResponseDto;
import com.programmers.domain.customer.dto.CustomerUpdateRequestDto;
import com.programmers.domain.customer.dto.CustomersResponseDto;
import com.programmers.io.Console;
import com.programmers.service.BlacklistService;
import com.programmers.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private static final String NORMAL_CUSTOMER_TYPE_NUMBER = "1";
    private static final String BLACKLIST_TYPE_NUMBER = "2";

    private final Console console;
    private final CustomerService customerService;
    private final BlacklistService blacklistService;

    public CustomerController(Console console, CustomerService customerService, BlacklistService blacklistService) {
        this.console = console;
        this.customerService = customerService;
        this.blacklistService = blacklistService;
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

    public void getCustomerList() {
        console.printCustomerListMessage();
        String command = console.readInput();
        checkCustomerTypeSelection(command);

        switch (command) {
            case NORMAL_CUSTOMER_TYPE_NUMBER -> getNormalCustomerList();
            case BLACKLIST_TYPE_NUMBER -> getBlacklist();
        }
    }

    private void checkCustomerTypeSelection(String input) {
        if (!input.equals(NORMAL_CUSTOMER_TYPE_NUMBER) && !input.equals(BLACKLIST_TYPE_NUMBER)) {
            throw new IllegalArgumentException();
        }
    }

    public void getNormalCustomerList() {
        console.printNormalCustomerListTitle();
        CustomersResponseDto customersResponseDto = customerService.findAll();
        console.printCustomers(customersResponseDto);
        log.info("The normal customer list has been printed.");
    }

    public List<String> getBlacklist() {
        console.printBlacklistTitle();
        List<String> blacklist = blacklistService.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }

    public void updateCustomer() {
        getNormalCustomerList();

        Customer originalCustomer = getCustomerToUpdate();
        CustomerUpdateRequestDto customerUpdateRequestDto = makeCustomerRequestDtoToUpdate(originalCustomer);

        customerService.update(customerUpdateRequestDto);
        console.printUpdateCustomerCompleteMessage();
    }

    public Customer getCustomerToUpdate() {
        console.printUpdateCustomerIdMessage();
        UUID updateCustomerId = UUID.fromString(console.readInput());

        CustomerResponseDto customerResponseDto = customerService.findById(updateCustomerId);

        return new Customer(customerResponseDto.id(), customerResponseDto.name());
    }

    private CustomerUpdateRequestDto makeCustomerRequestDtoToUpdate(Customer customer) {
        console.printUpdateNewCustomerNameMessage();
        String updateCustomerName = console.readInput();

        return new CustomerUpdateRequestDto(customer.getCustomerId(), updateCustomerName);
    }
}
