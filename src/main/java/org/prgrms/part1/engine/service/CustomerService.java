package org.prgrms.part1.engine.service;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(String name, String email) {
        return new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().withNano(0));
    }

    public Customer insertCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }
}
