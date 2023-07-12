package org.devcourse.springbasic.domain.customer.dao;

import org.devcourse.springbasic.domain.customer.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

    UUID save(Customer customer);
    void lastLoginUpdate(Customer customer);
    UUID update(Customer customer);
    List<Customer> findAll();
    Customer findById(UUID customerId);
    Customer findByName(String name);
    Customer findByEmail(String email);
}
