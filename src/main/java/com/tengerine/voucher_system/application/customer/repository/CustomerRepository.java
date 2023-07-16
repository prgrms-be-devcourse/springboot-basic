package com.tengerine.voucher_system.application.customer.repository;

import com.tengerine.voucher_system.application.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    List<Customer> findAllBlackCustomers();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    void deleteAll();

    void deleteById(UUID customerId);

}
