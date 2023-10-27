package com.programmers.vouchermanagement.customer.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.util.Validator;

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

    public CustomerResponse create(String name) {
        Validator.validateCustomerName(name);
        Customer customer = new Customer(UUID.randomUUID(), name);
        customerRepository.save(customer);
        return CustomerResponse.from(customer);
    }
}
