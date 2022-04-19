package com.blessing333.springbasic.customer.repository;

import com.blessing333.springbasic.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    UUID insert(Customer customer);

    void update(Customer customer);

    int count();

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    void deleteAll();
}
