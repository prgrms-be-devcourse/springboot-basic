package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByNickname(String nickname);

    Customer update(Customer customer);

    void delete(UUID customerId);

    void deleteAll();
}
