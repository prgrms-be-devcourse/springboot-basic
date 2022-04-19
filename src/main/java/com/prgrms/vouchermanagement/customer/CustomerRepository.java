package com.prgrms.vouchermanagement.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    void save(Customer customer);

    void update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerID);

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);
}
