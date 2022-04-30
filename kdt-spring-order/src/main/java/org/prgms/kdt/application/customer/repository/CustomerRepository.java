package org.prgms.kdt.application.customer.repository;

import java.util.Optional;
import java.util.UUID;
import org.prgms.kdt.application.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Customer update(Customer customer);

    int delete(UUID customerId);

    int deleteAll();
}
