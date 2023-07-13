package com.programmers.springmission.customer.repository;

import com.programmers.springmission.customer.domain.Customer;
import com.programmers.springmission.customer.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();

    List<Wallet> findWallet(UUID customerId);

    void updateName(Customer customer);

    void deleteById(UUID customerId);

    void deleteAll();
}
