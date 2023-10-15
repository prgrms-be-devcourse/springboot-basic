package com.zerozae.voucher.mock;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.repository.customer.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockCustomerRepository extends CustomerRepository {
    private final Map<UUID, Customer> customers = new HashMap<>();

    @Override
    public void save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }
}
