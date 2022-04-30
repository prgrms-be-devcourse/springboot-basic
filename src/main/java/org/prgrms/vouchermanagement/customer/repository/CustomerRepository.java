package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
  Customer insert(Customer customer);

  Customer update(Customer customer);

  int count();

  List<Customer> findAll();

  boolean checkExistenceById(UUID customerId);

  Optional<Customer> findById(UUID customerId);

  Optional<Customer> findByName(String name);

  Optional<Customer> findByEmail(String email);

  void deleteAll();
}
