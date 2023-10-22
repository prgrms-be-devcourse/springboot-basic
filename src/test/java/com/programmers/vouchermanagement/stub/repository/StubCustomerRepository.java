package com.programmers.vouchermanagement.stub.repository;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StubCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage = new HashMap<>();

    public StubCustomerRepository(List<Customer> customers) {
        customers.forEach(customer -> storage.put(customer.getId(), customer));
    }

    @Override
    public List<Customer> findAll(GetCustomersRequestDto request) {
        Stream<Customer> stream = storage.values().stream();

        if (request.isBlacklisted()) {
            stream = stream.filter(Customer::isBlacklisted);
        }

        return stream.collect(Collectors.toList());
    }
}
