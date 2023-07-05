package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.util.ConsoleMessages;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final Console console;
    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void findBlacklistCustomers() {
        List<Customer> customers = customerService.findBlacklistCustomers();
        List<CustomerDto> customerDtos = customers.stream()
                .map(Customer::toDto)
                .toList();

        console.printCustomers(customerDtos);
    }

    public void findCustomers() {
        List<Customer> customers = customerService.findCustomers();
        List<CustomerDto> customerDtos = customers.stream()
                .map(Customer::toDto)
                .toList();

        console.printCustomers(customerDtos);
    }

    public void createCustomer() {
        CustomerCreateRequest request = console.inputCustomerCreateInfo();

        UUID customerId = customerService.createCustomer(request.getEmail(), request.getName());

        String consoleMessage = MessageFormat.format(ConsoleMessages.CREATED_NEW_CUSTOMER, customerId);
        console.print(consoleMessage);
    }

    public void updateCustomer() {
        CustomerUpdateRequest request = console.inputCustomerUpdateInfo();

        customerService.updateCustomer(request.getCustomerId(), request.getName(), request.isBanned());

        console.print(ConsoleMessages.UPDATED_CUSTOMER);
    }

    public void deleteCustomer() {
        UUID customerId = console.inputUUID();

        customerService.deleteCustomer(customerId);

        console.print(ConsoleMessages.DELETED_CUSTOMER);
    }
}
