package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.model.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByNickname(String nickname);

    List<Customer> findAll();

    void update(Customer customer);

    void deleteById(UUID id);
}
