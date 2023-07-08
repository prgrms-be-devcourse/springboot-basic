package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByName(String name);

    Customer update(Customer customer);

    void deleteAll();

    List<List<String>> findAllBlackList();
}
