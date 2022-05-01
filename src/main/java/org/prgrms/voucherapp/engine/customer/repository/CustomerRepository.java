package org.prgrms.voucherapp.engine.customer.repository;

import org.prgrms.voucherapp.engine.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    void deleteById(UUID customerId);
}
