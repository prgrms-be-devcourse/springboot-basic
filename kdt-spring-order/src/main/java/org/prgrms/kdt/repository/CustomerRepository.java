package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    // Customer save(Customer); //insert, update를 한번에 이렇게 저리할 수 있음

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    int count();
}
