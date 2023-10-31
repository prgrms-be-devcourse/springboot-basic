package com.programmers.vouchermanagement.customer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.customer.service.CustomerService;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final ConsoleManager consoleManager;

    public CustomerController(CustomerService customerService, ConsoleManager consoleManager) {
        this.customerService = customerService;
        this.consoleManager = consoleManager;
    }

    public void readBlacklist() {
        List<CustomerResponse> blacklist = customerService.readBlacklist();
        consoleManager.printReadBlacklist(blacklist);
    }

    public void create(String name) {
        CustomerResponse customer = customerService.create(name);
        consoleManager.printSaveCustomerResult(customer);
    }

    public void readAllCustomers() {
        List<CustomerResponse> customers = customerService.findAll();
        consoleManager.printReadAllCustomers(customers);
    }

    public void findById(UUID customerId) {
        CustomerResponse customer = customerService.findById(customerId);
        consoleManager.printFoundCustomer(customer);
    }

    public void update(UpdateCustomerRequest request) {
        CustomerResponse customer = customerService.update(request);
        consoleManager.printSaveCustomerResult(customer);
    }

    public void deleteById(UUID customerId) {
        customerService.deleteById(customerId);
        consoleManager.printDeleteResult();
    }

    public void findVoucherOwner(UUID voucherId) {
        CustomerResponse customer = customerService.findByVoucherId(voucherId);
        consoleManager.printVoucherOwner(voucherId, customer);
    }
}
