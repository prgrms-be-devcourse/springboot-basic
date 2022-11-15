package com.programmers.customer.repository;

import com.programmers.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);

    void deleteAll();

    int count();

    Customer insert(Customer customer);
    Customer update(Customer customer);
    List<Customer> findAll();


}
