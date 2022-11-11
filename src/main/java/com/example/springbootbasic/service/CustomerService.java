package com.example.springbootbasic.service;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllBlackCustomers() {
        return customerRepository.findAllCustomers();
    }
}
