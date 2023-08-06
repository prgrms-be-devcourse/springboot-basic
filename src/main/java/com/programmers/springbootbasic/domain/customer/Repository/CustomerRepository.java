package com.programmers.springbootbasic.domain.customer.Repository;

import com.programmers.springbootbasic.domain.customer.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    UUID save(Customer customer);

    void update(Customer customer);

    Optional<Customer> findByEmail(String email);
}

