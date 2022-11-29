package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    Customer update(Customer customer);

    void delete(UUID customerId);

}
