package org.programmers.springorder.customer.repository;

import org.programmers.springorder.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository {
    List<Customer> findAllBlackList();
    List<Customer> findAll();
    Optional<Customer> findByID(UUID customerId);

    Customer insert(Customer customer);
}
