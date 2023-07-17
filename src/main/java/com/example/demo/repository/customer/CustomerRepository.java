package com.example.demo.repository.customer;

import com.example.demo.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    void save(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    void updateName(UUID id, String name);

    void deleteById(UUID id);
}
