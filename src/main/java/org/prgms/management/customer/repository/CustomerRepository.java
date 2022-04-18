package org.prgms.management.customer.repository;

import org.prgms.management.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
@Component
public interface CustomerRepository {
    Map<UUID, Customer> getAll();

    Optional<Customer> insert(Customer customer);

    Optional<Customer> update(Customer customer);

    Optional<Customer> getById(UUID customerId);

    Optional<Customer> getByName(String name);

    Optional<Customer> delete(UUID customerId);

    void deleteAll();
}
