package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Customer update(Customer customer);
    int count();
    List<Customer> findAll();
    List<Customer> findBlacklist();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByType(String email);
    Optional<Customer> findByVoucherId(String voucherId);

    void deleteAll();

    void deleteByCustomerId(String customerId);
}
