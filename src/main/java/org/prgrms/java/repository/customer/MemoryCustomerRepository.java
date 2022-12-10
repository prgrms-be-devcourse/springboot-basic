package org.prgrms.java.repository.customer;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return storage.values().stream()
                .filter(customer -> customer.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return storage.values().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<Customer> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Customer save(Customer customer) {
        if (findById(customer.getCustomerId()).isPresent()) {
            throw new CustomerException(String.format("Already exists customer having id %s", customer.getCustomerId()));
        }
        storage.put(customer.getCustomerId(), customer);
        return storage.get(customer.getCustomerId());
    }

    @Override
    public Customer update(Customer customer) {
        if (findById(customer.getCustomerId()).isEmpty()) {
            throw new CustomerException(String.format("No exists customer having id %s", customer.getCustomerId()));
        }
        storage.put(customer.getCustomerId(), customer);
        return storage.get(customer.getCustomerId());
    }

    @Override
    public void delete(UUID customerId) {
        if (findById(customerId).isEmpty()) {
            throw new CustomerException(String.format("No exists customer having id %s", customerId));
        }
        storage.remove(customerId);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
