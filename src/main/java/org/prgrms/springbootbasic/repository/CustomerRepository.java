package org.prgrms.springbootbasic.repository;

import org.prgrms.springbootbasic.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Optional<Customer> update(Customer customer);
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    int deleteAll();
    int deleteById(UUID customerId);
}
