package com.prgrm.kdt.customer.repository;

import com.prgrm.kdt.customer.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryCustomerRepository implements CustomerRepository{

    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public Map<UUID, Customer> findAll() {
        return storage;
    }

    @Override
    public Customer insert(Customer customer) {
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }
}
