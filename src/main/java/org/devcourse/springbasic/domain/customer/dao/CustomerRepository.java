package org.devcourse.springbasic.domain.customer.dao;

import org.devcourse.springbasic.domain.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    UUID save(Customer customer);
    UUID update(Customer customer);
    Optional<Customer> findById(UUID customerId);
    List<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();

    void deleteById(UUID customerId);
}
