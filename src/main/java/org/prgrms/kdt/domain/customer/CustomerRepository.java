package org.prgrms.kdt.domain.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(Long customerId);

    Optional<Customer> findByName(Name name);

    Optional<Customer> findByEmail(Email email);

    void deleteAll();

    int count();
}
