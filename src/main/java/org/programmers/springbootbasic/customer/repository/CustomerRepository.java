package org.programmers.springbootbasic.customer.repository;

import org.programmers.springbootbasic.customer.model.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
