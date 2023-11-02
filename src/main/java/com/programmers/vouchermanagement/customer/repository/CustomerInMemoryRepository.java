package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("memory")
public class CustomerInMemoryRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerInMemoryRepository.class);
    private final Map<UUID, Customer> customers;

    public CustomerInMemoryRepository() {
        customers = new HashMap<>();
    }

    @Override
    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
    }
}