package org.programmers.program.customer.repository;

import org.programmers.program.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    int update(Customer customer);
    void deleteAll();
    int count();
}
