package com.programmers.voucher.repository.customer;

import com.programmers.voucher.entity.customer.Customer;

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
