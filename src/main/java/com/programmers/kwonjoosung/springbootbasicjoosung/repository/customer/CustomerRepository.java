package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository {

    boolean insert(UUID customerId, String name);

    Customer findById(UUID customerId);

    List<Customer> findAll();

    boolean update(UUID customerId, String name);

    boolean delete(UUID customerId);


}
