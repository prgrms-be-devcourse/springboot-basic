package org.prgrms.weeklymission.customer.repository;

import org.prgrms.weeklymission.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String customerId);
    void save(Customer customer);
    List<Customer> findAll();
    int getStorageSize();
    void clear();
}
