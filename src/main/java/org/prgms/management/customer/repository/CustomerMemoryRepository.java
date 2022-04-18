package org.prgms.management.customer.repository;

import org.prgms.management.customer.entity.Customer;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerMemoryRepository implements CustomerRepository {
    private final Map<UUID, Customer> customerMap = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Customer> getAll() {
        return customerMap;
    }

    @Override
    public Optional<Customer> insert(Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        return Optional.ofNullable(
                customerMap.replace(customer.getCustomerId(), customer));
    }

    @Override
    public Optional<Customer> getById(UUID customerId) {
        return Optional.ofNullable(customerMap.get(customerId));
    }

    @Override
    public Optional<Customer> getByName(String name) {
        return customerMap.values().stream()
                .filter(customer -> Objects.equals(customer.getName(), name))
                .findFirst();
    }

    @Override
    public Optional<Customer> delete(UUID customerId) {
        return customerMap.values().stream()
                .filter(customer -> customer.getCustomerId() == customerId)
                .findFirst();
    }

    @Override
    public void deleteAll() {
        customerMap.clear();
    }
}
