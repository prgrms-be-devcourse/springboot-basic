package com.programmers.repository.customer;

import com.programmers.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID id);

    Customer update(Customer customer);

    void deleteById(UUID id);

    void deleteAll();
}
