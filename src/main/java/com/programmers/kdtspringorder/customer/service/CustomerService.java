package com.programmers.kdtspringorder.customer.service;

import com.programmers.kdtspringorder.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    void createCustomers(List<Customer> customers);

    Customer insert(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    Customer createCustomer(String email, String name);
}
