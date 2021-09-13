package org.prgms.order.customer.repository;

import org.prgms.order.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Customer update(Customer customer);

    int count();

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);

    List<Customer> findBlackList();
    void insertBlackListById(UUID customerId);
    void deleteAll();
    void deleteById(UUID customerId);
}

