package com.example.voucher_manager.domain.repository;

import com.example.voucher_manager.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
