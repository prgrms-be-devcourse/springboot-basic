package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void insert(Customer voucher);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    void deleteAll();
}
