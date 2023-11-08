package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.util.CustomerFileManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile({"file", "memory"})
public class CustomerFileRepository implements CustomerRepository {
    public final Map<UUID, Customer> customers;
    private final CustomerFileManager customerFileManager;

    public CustomerFileRepository(CustomerFileManager customerFileManager) {
        this.customerFileManager = customerFileManager;
        customers = customerFileManager.loadFile();
    }

    @Override
    public void insert(Customer customer) {
        customers.put(customer.getId(), customer);
        customerFileManager.saveFile(customers);
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return customers.values()
                .stream()
                .filter(Customer::getIsBlack)
                .toList();
    }
}