package com.example.commandlineapplication.domain.customer.repository;

import com.example.commandlineapplication.domain.customer.model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

  void save(Customer customer);

  void update(UUID customerId);

  Optional<Customer> findById(UUID customerId);

  List<Customer> findAll();

  void deleteById(UUID customerId);

  void deleteAll();

}
