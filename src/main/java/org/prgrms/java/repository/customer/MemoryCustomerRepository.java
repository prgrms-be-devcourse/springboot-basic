package org.prgrms.java.repository.customer;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public Collection<Customer> findAll() {
        return storage.values();
    }

    @Override
    public Customer insert(Customer customer) {
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
        return customer;
    }

    @Override
    public long deleteAll() {
        long count = storage.size();
        storage.clear();

        return count;
    }
}
