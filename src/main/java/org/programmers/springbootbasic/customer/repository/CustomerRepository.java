package org.programmers.springbootbasic.customer.repository;

import org.programmers.springbootbasic.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    void deleteById(UUID customerId);

    void deleteAll();
}
