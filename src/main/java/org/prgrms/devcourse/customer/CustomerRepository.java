package org.prgrms.devcourse.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    int count();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();

    void deleteAll();

}
