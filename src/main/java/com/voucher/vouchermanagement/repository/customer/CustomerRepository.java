package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();

    void deleteAll();
}
