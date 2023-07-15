package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.model.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer findById(UUID customerId);

    Customer findByNickname(String nickname);

    List<Customer> findAll();

    Customer update(Customer customer);

    Customer deleteById(UUID customerId);
}
