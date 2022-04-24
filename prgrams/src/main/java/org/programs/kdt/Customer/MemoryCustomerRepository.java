package org.programs.kdt.Customer;

import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class MemoryCustomerRepository implements CustomerRepository {

    private Map<UUID, Customer> storage = new LinkedHashMap<>();

    @Override
    public Customer insert(Customer customer) {
        synchronized (this) {
            storage.put(customer.getCustomerId(), customer);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public List<Customer> findByName(String name) {
        return storage.values().stream()
                .filter(customer -> customer.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return storage.values().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        storage = new LinkedHashMap<>();
    }

    @Override
    public List<Customer> findByType(String type) {
        return storage.values().stream()
                .filter(customer -> customer.getCustomerType().getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Customer> findEmailCustomer = storage.values().stream().filter(customer -> customer.getEmail().equals(email)).findFirst();
        if (findEmailCustomer.isPresent()) {
            storage.remove(findEmailCustomer.get().getCustomerId());
        } else {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_EMAIL);
        }
    }

    @Override
    public boolean existEmail(String email) {
        return storage.values().stream().anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public boolean existId(UUID customerId) {
        return storage.containsKey(customerId);
    }
}
