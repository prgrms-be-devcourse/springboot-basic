package com.prgms.management.customer.repository;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    Customer findById(UUID id);

    Customer findByEmail(String email);

    List<Customer> findByType(CustomerType type);

    List<Customer> findAll();

    void removeById(UUID id);

    void removeAll();
}
