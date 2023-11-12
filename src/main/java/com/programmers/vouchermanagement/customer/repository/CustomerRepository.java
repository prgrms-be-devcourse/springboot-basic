package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    void insert(Customer customer);

    List<Customer> findAll();

    List<Customer> findAllBlackCustomer();
}
