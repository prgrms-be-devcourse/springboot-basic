package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllBlacklist() {
        return customerRepository.findBlacklist();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(String name, String email, String type) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, type, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerRepository.insert(customer);
        return customer;
    }
}
