package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);
    Customer insert(Customer customer);

    Customer update(Customer customer);

    void deleteAll();
}
