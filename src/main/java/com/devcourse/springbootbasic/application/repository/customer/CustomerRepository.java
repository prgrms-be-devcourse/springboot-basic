package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> findAllBlackCustomers();
    Customer insert(Customer customer);
    Customer update(Customer customer);
    List<Customer> findAllCustomers();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    void deleteAll();
    int count();
    void setFilePath(String filePath);
}
