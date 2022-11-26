package com.programmers.VoucherManagementApplication.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    void deleteById(UUID customerId);

    int count();
}
