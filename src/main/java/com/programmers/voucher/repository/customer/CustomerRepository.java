package com.programmers.voucher.repository.customer;

import com.programmers.voucher.model.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllBlack();
}
