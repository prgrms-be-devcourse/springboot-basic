package com.example.springbootbasic.repository;

import com.example.springbootbasic.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllCustomers();
}
