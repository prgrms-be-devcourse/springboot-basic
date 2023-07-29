package com.tangerine.voucher_system.application.customer.repository;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    List<Customer> findAllBlackCustomers();

    Optional<Customer> findById(UUID customerId);

    List<Customer> findByName(Name name);

    void deleteById(UUID customerId);

}
