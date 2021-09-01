package org.prgrms.kdt.engine.customer.repository;

import org.prgrms.kdt.engine.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository {
    int count();
    Customer insert(Customer customer);
    Customer update(Customer customer);
    Optional<List<Customer>> findAll();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    void deleteAll();
}
