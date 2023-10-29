package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
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
        return customers.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Customer> findById(UUID customerID) {
        return Optional.ofNullable(customers.get(customerID));
    }

    @Override
    public void delete(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }

    @Override
    public void update(UUID customerId, CustomerUpdateRequest customerRequest) {
        Customer customer = customers.get(customerId);
        customer.updateCustomerInfo(customerRequest);
    }
}
