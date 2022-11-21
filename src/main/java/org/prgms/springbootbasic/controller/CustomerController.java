package org.prgms.springbootbasic.controller;


import org.prgms.springbootbasic.domain.customer.BlacklistedCustomer;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerCreateDTO;
import org.prgms.springbootbasic.service.BlacklistedCustomerService;
import org.prgms.springbootbasic.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final BlacklistedCustomerService blacklistedCustomerService;


    public CustomerController(CustomerService customerService, BlacklistedCustomerService blacklistedCustomerService) {
        this.customerService = customerService;
        this.blacklistedCustomerService = blacklistedCustomerService;
    }

    public List<Customer> customerList() {
        return customerService.findAll();
    }

    public Customer createCustomer(CustomerCreateDTO customerCreateDTO) {
        return customerService.createCustomer(customerCreateDTO);
    }

    public Customer updateLastLoginTime(Customer customer) {
        return customerService.updateLastLoginAt(customer);
    }

    public Optional<Customer> findOneCustomer(UUID customerId) {
        return customerService.findOneCustomer(customerId);
    }

    public List<BlacklistedCustomer> blacklistedCustomerList() {
        return blacklistedCustomerService.findAll();
    }

    public List<Customer> findCustomers(UUID voucherId) {
        return customerService.findCustomers(voucherId);
    }
}
