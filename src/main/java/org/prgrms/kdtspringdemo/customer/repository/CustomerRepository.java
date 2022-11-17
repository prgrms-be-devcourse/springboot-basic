package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    //CREATE
    Customer insert(Customer customer);
    //READ

    Optional<Customer> findById(UUID targetId);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByName(String name);

    List<Customer> findAll();

    //UPDATE
    int update(Customer customer);

    //DELETE
    int deleteById(UUID targetId);

    int deleteAll();

    int count();
}
