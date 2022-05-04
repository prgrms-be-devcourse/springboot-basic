package org.programmers.springbootbasic.customer.service;

import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.customer.repository.CustomerRepository;
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
