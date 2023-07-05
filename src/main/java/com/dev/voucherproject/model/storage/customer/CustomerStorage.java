package com.dev.voucherproject.model.storage.customer;

import com.dev.voucherproject.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerStorage {
    void insert(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    void update(Customer customer);
    void deleteAll();

    void deleteById(UUID customerId);
}
