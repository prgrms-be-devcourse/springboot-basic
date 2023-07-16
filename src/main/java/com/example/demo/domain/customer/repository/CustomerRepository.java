package com.example.demo.domain.customer.repository;

import com.example.demo.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    void updateName(UUID id, String name);

    void deleteById(UUID id);
}
