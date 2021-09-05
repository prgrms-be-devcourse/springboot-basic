package org.programmers.customer.repository;

import org.programmers.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void save(Customer customer);

    void update(String email, String name);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    void deleteByEmail(String email);

    void deleteAll();
}
