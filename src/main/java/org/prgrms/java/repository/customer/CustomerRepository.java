package org.prgrms.java.repository.customer;

import org.prgrms.java.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String name);

    List<Customer> findAll();

    Customer update(Customer customer);

    void delete(UUID customerId);

    long deleteAll();
}
