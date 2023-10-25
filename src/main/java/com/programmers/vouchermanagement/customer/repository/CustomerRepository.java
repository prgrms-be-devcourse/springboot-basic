package com.programmers.vouchermanagement.customer.repository;

import java.util.List;

import com.programmers.vouchermanagement.customer.domain.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);

    List<Customer> findAll();

    List<Customer> findBlackCustomers();

    void deleteAll();
}
