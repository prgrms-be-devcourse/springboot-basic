package com.programmers.vouchermanagement.customer.service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerDto;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> readAllBlackCustomer() {
        List<Customer> blacklist = customerRepository.findAllBlackCustomer();
        if (blacklist.isEmpty()) {
            return Collections.emptyList();
        }
        return blacklist.stream().map(CustomerDto::from).toList();
    }
}
