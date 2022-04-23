package com.example.voucher_manager.domain.customer;

import com.example.voucher_manager.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findCustomerHasVoucher(UUID voucherId);
    Optional<Customer> findByEmail(String email);
    Customer update(Customer customer);
    Optional<Customer> insert(Customer customer);
    void clear();
}
