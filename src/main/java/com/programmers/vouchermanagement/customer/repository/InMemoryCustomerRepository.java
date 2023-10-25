package com.programmers.vouchermanagement.customer.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.customer.domain.Customer;

@Repository
@Profile("dev")
public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> customers;

    public InMemoryCustomerRepository() {
        this.customers = new HashMap<>();
    }

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        if (customers.isEmpty()) {
            return Collections.emptyList();
        }
        return customers.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }
}
