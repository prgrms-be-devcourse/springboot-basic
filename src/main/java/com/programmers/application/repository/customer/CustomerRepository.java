package com.programmers.application.repository.customer;

import com.programmers.application.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findByCustomerId(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    Customer update(Customer customer);

    void deleteByCustomerId(UUID customerId);

    int countAllCustomers();
}
