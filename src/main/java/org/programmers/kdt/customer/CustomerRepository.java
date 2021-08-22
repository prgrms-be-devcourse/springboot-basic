package org.programmers.kdt.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer registerToBlackList(Customer customer);
    Optional<Customer> findById(UUID customerId);
    Optional<List<Customer>> findByName(String name);
    Optional<List<Customer>> findByEmail(String name);
    List<Customer> findBlackListCustomers();
    List<Customer> findAll();
}
