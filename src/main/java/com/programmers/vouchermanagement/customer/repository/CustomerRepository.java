package com.programmers.vouchermanagement.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.vouchermanagement.customer.domain.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    List<Customer> findBlackCustomers();

    void deleteById(UUID customerId);

    void deleteAll();
}
