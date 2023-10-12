package com.programmers.springbootbasic.repository.customer;

import com.programmers.springbootbasic.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllBlacklisted();
}
