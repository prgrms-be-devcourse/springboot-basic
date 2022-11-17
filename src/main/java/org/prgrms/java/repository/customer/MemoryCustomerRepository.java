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
    private final Map<UUID, Customer> blackStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(UUID customerId, boolean isBlocked) {
        if (isBlocked) {
            return Optional.ofNullable(blackStorage.get(customerId));
        }
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public Collection<Customer> findAll(boolean isBlocked) {
        if (isBlocked) {
            return blackStorage.values();
        }
        return storage.values();
    }

    @Override
    public Customer insert(Customer customer) {
        if (findById(customer.getCustomerId(), true).isPresent() || findById(customer.getCustomerId(), false).isPresent()) {
            throw new CustomerException(String.format("Already exists customer having id %s", customer.getCustomerId()));
        }
        if (customer.isBlocked()) {
            blackStorage.put(customer.getCustomerId(), customer);
            return blackStorage.get(customer.getCustomerId());
        }
        storage.put(customer.getCustomerId(), customer);
        return storage.get(customer.getCustomerId());
    }

    @Override
    public long deleteAll() {
        long count;
        count = storage.size() + blackStorage.size();

        storage.clear();
        blackStorage.clear();

        return count;
    }
}
