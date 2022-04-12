package org.prgrms.weeklymission.customer.repository;

import org.prgrms.weeklymission.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(UUID customerId);
    List<Customer> findAll();
    int countStorageSize();
    void clear();
}
