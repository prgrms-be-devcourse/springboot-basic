package com.programmers.springbootbasic.domain.customer.repository;

import com.programmers.springbootbasic.domain.customer.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findByEmail(String email);

    Customer save(Customer customer);

    int update(Customer customer);

    List<Customer> findAll();

    List<Customer> findBlacklist();

    void deleteAll();
}
