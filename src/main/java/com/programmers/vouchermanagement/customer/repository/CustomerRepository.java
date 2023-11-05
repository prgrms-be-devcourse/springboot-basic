package com.programmers.vouchermanagement.customer.repository;

import java.util.List;

import com.programmers.vouchermanagement.customer.domain.Customer;

public interface CustomerRepository {
    List<Customer> findAllBlackCustomer();
}
