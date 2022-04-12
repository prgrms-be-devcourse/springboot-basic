package org.prgrms.weeklymission.customer.repository;

import org.prgrms.weeklymission.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(String customerId);
    List<Customer> findAll();
    int countStorageSize();
    void clear();
}
