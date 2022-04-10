package com.example.voucher_manager.domain.service;

import com.example.voucher_manager.domain.customer.Customer;
import com.example.voucher_manager.domain.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
