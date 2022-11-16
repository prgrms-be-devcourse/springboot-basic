package com.programmers.customer.service;

import com.programmers.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void join(Customer customer);

    Customer findById(UUID customerId);
    Customer findByName(String name);
    Customer findByEmail(String email);

    Customer update(Customer customer);

    List<Customer> findAll();
}
