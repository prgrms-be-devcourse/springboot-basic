package com.programmers.springbasic.domain.customer.repository;

import com.programmers.springbasic.domain.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    void update(Customer customer);

    void delete(UUID customerId);
}
