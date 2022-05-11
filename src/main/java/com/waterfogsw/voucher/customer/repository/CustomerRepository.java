package com.waterfogsw.voucher.customer.repository;

import com.waterfogsw.voucher.customer.model.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();

    Customer findById(long id);

    void deleteById(long id);
}
