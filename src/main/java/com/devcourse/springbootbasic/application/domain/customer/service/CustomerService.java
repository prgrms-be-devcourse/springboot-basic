package com.devcourse.springbootbasic.application.domain.customer.service;

import com.devcourse.springbootbasic.application.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<String> getBlackCustomers() {
        return customerRepository.findAllBlackCustomers();
    }
}
