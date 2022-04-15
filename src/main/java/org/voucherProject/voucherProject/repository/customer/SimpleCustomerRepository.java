package org.voucherProject.voucherProject.repository.customer;

import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.customer.Customer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleCustomerRepository implements CustomerRepository{

    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.of(storage.get(customerId));
    }

    @Override
    public Optional<Customer> findByName(String customerName) {
        return storage.values().stream().filter(c -> c.getCustomerName().equals(customerName)).findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String customerEmail) {
        return storage.values().stream().filter(c -> c.getCustomerEmail().equals(customerEmail)).findFirst();
    }


    @Override
    public List<Customer> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Customer save(Customer customer) {
        if (findById(customer.getCustomerId()).isPresent()) {
            throw new RuntimeException("동일한 아이디가 존재합니다.");
        }
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
