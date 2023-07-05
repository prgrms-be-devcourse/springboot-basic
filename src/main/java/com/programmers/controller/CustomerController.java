package com.programmers.controller;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.dto.CustomerCreateRequestDto;
import com.programmers.domain.customer.dto.CustomerResponseDto;
import com.programmers.domain.customer.dto.CustomerUpdateRequestDto;
import com.programmers.domain.customer.dto.CustomersResponseDto;
import com.programmers.exception.EmptyException;
import com.programmers.exception.InvalidInputException;
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
    private static final String DELETE_ONE_CUSTOMER_NUMBER = "1";
    private static final String DELETE_ALL_CUSTOMERS_NUMBER = "2";

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
        if (input.isEmpty()) {
            throw new EmptyException("[ERROR] Customer Type 번호가 입력되지 않았습니다.");
        }

        if (!input.equals(NORMAL_CUSTOMER_TYPE_NUMBER) && !input.equals(BLACKLIST_TYPE_NUMBER)) {
            throw new InvalidInputException("[ERROR] 입력하신 Customer Type 번호가 유효하지 않습니다.");
        }
    }

    public List<Customer> getNormalCustomerList() {
        console.printNormalCustomerListTitle();
        CustomersResponseDto customersResponseDto = customerService.findAll();

        List<Customer> customers = customersResponseDto.customers();
        if (customers.isEmpty()) {
            console.printNormalCustomerListEmptyMessage();
            return customers;
        }

        console.printCustomers(customers);
        log.info("The normal customer list has been printed.");
        return customers;
    }

    public List<String> getBlacklist() {
        console.printBlacklistTitle();
        List<String> blacklist = blacklistService.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }

    public void updateCustomer() {
        if (getNormalCustomerList().isEmpty()) {
            return;
        }

        Customer originalCustomer = getCustomerToUpdate();
        CustomerUpdateRequestDto customerUpdateRequestDto = makeCustomerRequestDtoToUpdate(originalCustomer);

        customerService.update(customerUpdateRequestDto);
        console.printUpdateCustomerCompleteMessage();
        log.info("The customer has been updated.");
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

    public void deleteCustomer() {
        if (getNormalCustomerList().isEmpty()) {
            return;
        }

        console.printDeleteTypeCustomerSelectionMessage();
        String command = console.readInput();
        checkDeleteTypeSelection(command);

        switch (command) {
            case DELETE_ONE_CUSTOMER_NUMBER -> deleteOneCustomer();
            case DELETE_ALL_CUSTOMERS_NUMBER -> deleteAllCustomers();
        }
    }

    private void checkDeleteTypeSelection(String input) {
        if (input.isEmpty()) {
            throw new EmptyException("[ERROR] Delete Type 번호가 입력되지 않았습니다.");
        }

        if (!input.equals(DELETE_ONE_CUSTOMER_NUMBER) && !input.equals(DELETE_ALL_CUSTOMERS_NUMBER)) {
            throw new InvalidInputException("[ERROR] 입력하신 Delete Type 번호가 유효하지 않습니다.");
        }
    }

    public void deleteOneCustomer() {
        console.printDeleteCustomerIdMessage();
        UUID deleteCustomerId = UUID.fromString(console.readInput());

        customerService.deleteById(deleteCustomerId);
        console.printDeleteCustomerCompleteMessage();
        log.info("The customer has been deleted.");

    }

    public void deleteAllCustomers() {
        customerService.deleteAll();
        console.printDeleteAllCustomersCompleteMessage();
        log.info("All customers have been deleted.");
    }
}
