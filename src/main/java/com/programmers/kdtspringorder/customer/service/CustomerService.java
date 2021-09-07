package com.programmers.kdtspringorder.customer.service;

import com.programmers.kdtspringorder.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    void createCustomers(List<com.programmers.kdtspringorder.customer.model.Customer> customers);

    com.programmers.kdtspringorder.customer.model.Customer insert(com.programmers.kdtspringorder.customer.model.Customer customer);

    Optional<com.programmers.kdtspringorder.customer.model.Customer> findById(UUID customerId);

    List<com.programmers.kdtspringorder.customer.model.Customer> findAll();

    Customer createCustomer(String email, String name);
}
