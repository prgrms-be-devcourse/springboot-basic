package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID userId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    int count();

    void delete(Customer customer);
    void deleteById(UUID customerId);
    void deleteAll();
}
