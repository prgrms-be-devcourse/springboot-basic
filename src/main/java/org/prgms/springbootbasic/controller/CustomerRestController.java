package org.prgms.springbootbasic.controller;


import org.prgms.springbootbasic.domain.customer.BlacklistedCustomer;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerCreateDTO;
import org.prgms.springbootbasic.service.BlacklistedCustomerService;
import org.prgms.springbootbasic.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class CustomerRestController {

    private final CustomerService customerService;
    private final BlacklistedCustomerService blacklistedCustomerService;


    public CustomerRestController(CustomerService customerService, BlacklistedCustomerService blacklistedCustomerService) {
        this.customerService = customerService;
        this.blacklistedCustomerService = blacklistedCustomerService;
    }

    @GetMapping("/customers")
    public List<Customer> customerList() {
        return customerService.findAll();
    }

    @PostMapping("/customers/new")
    public Customer createCustomer(CustomerCreateDTO customerCreateDTO) {
        return customerService.createCustomer(customerCreateDTO);
    }

    public Customer updateLastLoginTime(Customer customer) {
        return customerService.updateLastLoginAt(customer);
    }

    @GetMapping("customers/detail/{customerId}")
    public Customer findOneCustomer(@PathVariable UUID customerId) {
        Customer customer = customerService.findOneCustomer(customerId).orElseThrow(NoSuchElementException::new);
        this.updateLastLoginTime(customer);
        return customer;
    }

    @GetMapping("/customers/blacklist")
    public List<BlacklistedCustomer> blacklistedCustomerList() {
        return blacklistedCustomerService.findAll();
    }

    @GetMapping("/customers/{voucherId}")
    public List<Customer> findCustomers(@PathVariable UUID voucherId) {
        return customerService.findCustomers(voucherId);
    }
}
