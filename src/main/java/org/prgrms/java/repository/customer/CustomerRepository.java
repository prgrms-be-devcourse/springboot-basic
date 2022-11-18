package org.prgrms.java.repository.customer;

import org.prgrms.java.domain.customer.Customer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findById(UUID customerId);

    Collection<Customer> findAll();

    Customer insert(Customer customer);

    Customer update(Customer customer);

    long deleteAll();
}
