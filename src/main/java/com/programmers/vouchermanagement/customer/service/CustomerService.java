package com.programmers.vouchermanagement.customer.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> readBlacklist() {
        List<Customer> blacklist = customerRepository.findBlackCustomers();

        if (blacklist.isEmpty()) {
            return Collections.emptyList();
        }

        return blacklist.stream()
                .map(CustomerResponse::from)
                .toList();
    }
}
