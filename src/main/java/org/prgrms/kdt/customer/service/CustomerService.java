package org.prgrms.kdt.customer.service;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllBlacklist() {
        return customerRepository.findAllBlacklist();
    }

    public Customer createCustomer(final String customerName) {
        return new Customer(UUID.randomUUID(), customerName, false);
    }

    public void addCustomer(final Customer customer) {
        customerRepository.insert(customer);
    }
}
