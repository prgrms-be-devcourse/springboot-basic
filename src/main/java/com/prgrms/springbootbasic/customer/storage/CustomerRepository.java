package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    void delete(UUID id);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByName(String name);

    List<Customer> findAll();
}
