package org.prgrms.kdt.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Customer;

public interface CustomerRepository {

  List<Customer> findAll();

  Optional<Customer> findById(UUID customerId);

  boolean existsByEmail(String email);

  Optional<Customer> save(Customer customer);

  void deleteAll();

  Optional<Customer> findCustomerByVoucherId(UUID voucherId);
}