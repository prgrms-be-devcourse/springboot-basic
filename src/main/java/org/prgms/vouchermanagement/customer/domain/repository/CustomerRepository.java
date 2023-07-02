package org.prgms.vouchermanagement.customer.domain.repository;

import org.prgms.vouchermanagement.customer.domain.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findByID(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();
}
