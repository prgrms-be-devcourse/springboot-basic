package com.programmers.vouchermanagement.customer;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> readBlacklist() {
        List<Customer> blacklist = customerRepository.findBlackCustomers();
        //TODO: logger to inform no black customers exists
        return blacklist;
    }
}
