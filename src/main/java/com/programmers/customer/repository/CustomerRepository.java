package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;

import java.util.List;
import java.util.UUID;

//4차 PR
public interface CustomerRepository {

    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(UUID customerId);

    void deleteById(UUID customerId);
}
