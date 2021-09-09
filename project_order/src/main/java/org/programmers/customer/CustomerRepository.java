package org.programmers.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    int countAll();

    int countBlack();

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByVoucherId(UUID voucherId);

    List<Customer> findBlackCustomers();

    void deleteAll();

}
