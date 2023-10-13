package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllBlacklisted();
}
