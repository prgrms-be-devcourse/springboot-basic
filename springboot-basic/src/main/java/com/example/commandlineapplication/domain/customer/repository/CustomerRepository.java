package com.example.commandlineapplication.domain.customer.repository;

import com.example.commandlineapplication.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

  void save(Customer customer);

  void update(Customer customer);

  Optional<Customer> findById(UUID customerId);

  List<Customer> findAll();

  void deleteById(UUID customerId);
}
