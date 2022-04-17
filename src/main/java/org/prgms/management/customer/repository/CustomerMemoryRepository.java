package org.prgms.management.customer.repository;

import org.prgms.management.customer.entity.Customer;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO : CRUD 구현
// TODO : update, insert는 RuntimeException을 발생시키는건 어떨까
// TODO : 그 외에는 전부 try catch로 log를 기록한다.
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
}
