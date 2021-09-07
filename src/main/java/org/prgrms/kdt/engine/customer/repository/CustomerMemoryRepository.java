package org.prgrms.kdt.engine.customer.repository;

import org.prgrms.kdt.engine.customer.domain.Customer;

import java.util.*;

public class CustomerMemoryRepository implements CustomerRepository{
    Map<UUID, Customer> storage = new HashMap<>();

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public Customer insert(Customer customer) {
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Optional<List<Customer>> findAll() {
        if (storage.isEmpty()) return Optional.empty();
        return Optional.of(new ArrayList<>(storage.values()));
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        for (Customer customer : storage.values()) {
            if (name.equals(customer.getName()))
                return Optional.of(customer);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        for (Customer customer : storage.values()) {
            if (email.equals(customer.getEmail()))
                return Optional.of(customer);
        }
        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
