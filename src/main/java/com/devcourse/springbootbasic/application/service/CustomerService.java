package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.domain.customer.Customer;
import com.devcourse.springbootbasic.application.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBlackCustomers() {
        return customerRepository.findAll();
    }
}
