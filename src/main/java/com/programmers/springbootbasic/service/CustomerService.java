package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> blacklist() {
        return customerRepository.findAllBlacklisted();
    }
}
