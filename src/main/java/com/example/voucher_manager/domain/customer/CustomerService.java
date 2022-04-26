package com.example.voucher_manager.domain.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final BlacklistCustomerRepository blacklistCustomerRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(BlacklistCustomerRepository blacklistCustomerRepository, CustomerRepository customerRepository) {
        this.blacklistCustomerRepository = blacklistCustomerRepository;
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findAllBlackList() {
        return blacklistCustomerRepository.findAll();
    }

    public Customer signUp(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        customerRepository.insert(customer);
        return customer;
    }
}
