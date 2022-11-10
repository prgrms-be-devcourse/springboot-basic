package com.programmers.customer.repository;

import com.programmers.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllBlackList();
}
