package com.prgrm.kdt.customer.repository;

import com.prgrm.kdt.customer.domain.Customer;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findById(UUID customerId);
    Map<UUID, Customer> findAll();
    Customer insert(Customer customer);
}
