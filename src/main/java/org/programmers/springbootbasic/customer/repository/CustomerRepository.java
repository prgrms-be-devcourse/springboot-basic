package org.programmers.springbootbasic.customer.repository;

import org.programmers.springbootbasic.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findById(Long customerId);

    List<Customer> findAll();

    void remove(Long customerId);

}