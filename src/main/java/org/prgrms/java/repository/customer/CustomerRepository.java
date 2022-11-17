package org.prgrms.java.repository.customer;

import org.prgrms.java.domain.customer.Customer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findById(UUID customerId, boolean isBlocked);

    Collection<Customer> findAll(boolean isBlocked);

    Customer insert(Customer customer);

    long deleteAll();
}
