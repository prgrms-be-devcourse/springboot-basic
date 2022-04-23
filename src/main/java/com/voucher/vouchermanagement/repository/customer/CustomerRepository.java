package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.model.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    void insert(Customer voucher);

    List<Customer> findAll();

    void deleteAll();
}
