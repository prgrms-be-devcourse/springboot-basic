package com.programmers.vouchermanagement.customer.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    private static final String NO_BLACKLIST = "no blacklist";
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> readBlacklist() {
        List<Customer> blacklist = customerRepository.findBlackCustomers();
        if (blacklist.isEmpty()) {
            throw new NoSuchElementException(NO_BLACKLIST);
        }
        return blacklist;
    }
}
