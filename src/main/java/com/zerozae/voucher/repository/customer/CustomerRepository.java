package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer save(Customer customer);
    List<Customer> findAll();
}
