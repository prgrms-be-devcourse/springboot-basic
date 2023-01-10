package org.prgrms.kdt.model.customer.repository;

import org.prgrms.kdt.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> getAllStoredCustomer();

    void clear();

    Optional<Customer> findById(UUID customerId);
}
