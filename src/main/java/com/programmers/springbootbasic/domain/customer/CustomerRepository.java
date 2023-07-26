package com.programmers.springbootbasic.domain.customer;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> save(Customer customer);

    Optional<Customer> update(Customer customer);

    Optional<Customer> findByEmail(String email);
}

