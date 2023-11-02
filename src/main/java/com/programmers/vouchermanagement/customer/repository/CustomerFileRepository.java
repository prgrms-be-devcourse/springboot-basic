package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("file")
public class CustomerFileRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerFileRepository.class);
    private final CustomerFileManager customerFileManager;

    public CustomerFileRepository(CustomerFileManager customerFileManager) {
        this.customerFileManager = customerFileManager;
    }

    @Override
    public void save(Customer customer) {
        customerFileManager.customers.put(customer.getId(), customer);
        customerFileManager.saveFile();
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return customerFileManager.customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
    }
}