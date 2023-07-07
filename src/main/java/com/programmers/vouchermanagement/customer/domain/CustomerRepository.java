package com.programmers.vouchermanagement.customer.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    void update(Customer customer);

    void deleteById(UUID id);
}
