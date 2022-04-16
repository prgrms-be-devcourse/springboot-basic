package org.prgrms.vouchermanager.customer.repository;

import org.prgrms.vouchermanager.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(UUID customerId);

    Optional<Customer> findByEmail(UUID customerId);

    void delete(UUID customerId);

    void deleteAll();
}
