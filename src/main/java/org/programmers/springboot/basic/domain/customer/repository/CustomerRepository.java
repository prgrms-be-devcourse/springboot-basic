package org.programmers.springboot.basic.domain.customer.repository;

import org.programmers.springboot.basic.domain.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    void add(Customer customer);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findById(UUID customerId);
    List<Customer> getAll();
    List<Customer> getBlack();
    void update(Customer customer);
    void delete(Customer customer);
    void deleteAll();
}
