package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    void insert(Customer customer);

    void update(String email, String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();

    void deleteByEmail(String email);

    void deleteAll();

}
