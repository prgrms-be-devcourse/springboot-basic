package org.programmers.springorder.customer.repository;

import org.programmers.springorder.customer.model.Customer;

import java.util.List;


public interface CustomerRepository {
    List<Customer> findAllBlackList();
    List<Customer> findAll();
}
