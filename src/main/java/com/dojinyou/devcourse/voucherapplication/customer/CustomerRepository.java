package com.dojinyou.devcourse.voucherapplication.customer;

import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer create(Customer customer);
    Optional<Customer> findById(long id);
    List<Customer> findAll();
    void deleteById(long id);
}
