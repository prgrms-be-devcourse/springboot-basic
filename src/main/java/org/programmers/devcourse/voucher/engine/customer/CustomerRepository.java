package org.programmers.devcourse.voucher.engine.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

  Optional<Customer> getById(UUID customerId);

  Optional<Customer> getByName(String name);

  Optional<Customer> getByEmail(String email);

  Customer insert(Customer customer);

  List<Customer> getAll();

  int deleteAll();

  int delete(Customer customer);

  Customer update(Customer customer);
  
}
