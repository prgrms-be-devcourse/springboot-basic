package com.mountain.voucherApp.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Customer update(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    int count();
    void deleteAll();
}
