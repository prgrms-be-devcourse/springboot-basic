package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.repository.customer.CustomerRepository;
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
