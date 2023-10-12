package com.programmers.springbootbasic.domain.customer.repository;

import com.programmers.springbootbasic.domain.customer.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
