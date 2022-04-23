package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    void deleteAll();
}
