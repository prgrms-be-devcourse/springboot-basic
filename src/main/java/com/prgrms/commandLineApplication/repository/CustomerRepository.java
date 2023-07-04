package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
  Customer save(Customer customer);

  Customer update(Customer customer);

  List<Customer> findAll();

  Optional<Customer> findById(UUID customerId);

  Optional<Customer> findByName(String name);

  Optional<Customer> findByEmail(String email);

  void deleteAll();

}
