package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerJdbcRepository implements CustomerRepository {
    @Override
    public void save(Customer customer) {

    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(UUID customerId) {

    }
}
