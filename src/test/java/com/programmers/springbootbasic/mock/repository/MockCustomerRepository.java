package com.programmers.springbootbasic.mock.repository;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.repository.customer.CustomerRepository;

import java.util.*;

public class MockCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage = new HashMap<>();

    public MockCustomerRepository(List<Customer> customers) {
        customers.forEach(customer -> storage.put(customer.getId(), customer));
    }

    @Override
    public List<Customer> findAllBlacklisted() {
        return new ArrayList<>(storage.values());
    }
}
