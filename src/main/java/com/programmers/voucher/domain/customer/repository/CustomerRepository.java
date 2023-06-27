package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
