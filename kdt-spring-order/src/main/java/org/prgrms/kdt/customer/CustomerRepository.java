package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    // save = insert + update
    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID userId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    int count();

    void deleteAll();
}
