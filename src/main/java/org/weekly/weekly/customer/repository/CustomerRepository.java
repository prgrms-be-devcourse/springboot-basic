package org.weekly.weekly.customer.repository;

import org.weekly.weekly.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer insert(Customer customer);
    void deleteByEmail(String email);
    void deleteAll();
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();

    Customer update(Customer customer);
}

