package org.programmers.springbootbasic.application.customer.service;

import org.programmers.springbootbasic.application.customer.model.Customer;
import org.programmers.springbootbasic.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
