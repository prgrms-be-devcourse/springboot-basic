package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> createCustomer(String name) {
        Customer newCustomer = new Customer(UUID.randomUUID(), name);
        return customerRepository.insert(newCustomer) ? Optional.of(newCustomer) : Optional.empty();
    }

    public Optional<Customer> findCustomerByCustomerId(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean updateCustomer(UUID customerId, String customerName) {
        Customer customer = new Customer(customerId, customerName);
        return customerRepository.update(customer);
    }

    public boolean deleteCustomer(UUID customerId) {
        return customerRepository.delete(customerId);
    }

    public List<Customer> getBlackList() {
        return customerRepository.findAllBlockCustomer();
    }
}
