package com.programmers.vouchermanagement.mock.repository;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MockCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage = new HashMap<>();

    public MockCustomerRepository(List<Customer> customers) {
        customers.forEach(customer -> storage.put(customer.getId(), customer));
    }

    @Override
    public List<Customer> findAllBlacklisted() {
        return storage.values().stream()
                .filter(Customer::isBlacklisted)
                .collect(Collectors.toList());
    }
}
