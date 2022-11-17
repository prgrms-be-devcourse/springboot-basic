package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> findAll();

    Customer save(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);
}
