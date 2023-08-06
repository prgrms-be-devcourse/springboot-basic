package com.programmers.springbootbasic.domain.customer.Repository;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.domain.customer.CustomerUpdateDto;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    UUID save(Customer customer);

    void update(CustomerUpdateDto customer);

    Optional<Customer> findByEmail(String email);
}

