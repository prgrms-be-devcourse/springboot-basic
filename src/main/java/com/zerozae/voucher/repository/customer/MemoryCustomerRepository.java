package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class MemoryCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> customers;

    public MemoryCustomerRepository() {
        this.customers = new ConcurrentHashMap<>();
    }

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public Optional<Customer> findById(UUID customerID) {
        return Optional.ofNullable(customers.get(customerID));
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }

    @Override
    public void update(UUID customerId, CustomerRequest customerRequest) {
        Customer customer = customers.get(customerId);
        customer.updateCustomerInfo(customerRequest);
    }
}
