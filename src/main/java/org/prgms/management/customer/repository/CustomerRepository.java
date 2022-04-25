package org.prgms.management.customer.repository;

import org.prgms.management.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface CustomerRepository {
    Optional<Customer> insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> update(Customer customer);

    Optional<Customer> delete(Customer customer);

    void deleteAll();
}
