package com.prgrms.commandLineApplication.repository.customer;

import com.prgrms.commandLineApplication.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

  Customer save(Customer customer);

  String update(String email, String customerName);

  void deleteAll();

  void deleteById(UUID customerId);

  List<Customer> findAll();

  Customer findById(UUID customerId);

  Customer findByEmail(String email);

}
