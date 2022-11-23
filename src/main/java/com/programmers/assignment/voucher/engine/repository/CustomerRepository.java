package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(long customerId);

    Optional<Customer> findByUuid(UUID customerUuid);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    int count();

}
