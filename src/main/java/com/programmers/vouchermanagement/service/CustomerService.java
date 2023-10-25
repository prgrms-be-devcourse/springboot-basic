package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    List<Customer> findBannedCustomers() {
        return customerRepository.findBannedCustomers();
    }

    List<Customer> findByCustomerName(String name) {
        return customerRepository.findByName(name);
    }

    Customer createCustomer(String name) {
        Customer customer = new Customer(UUID.randomUUID(), name, LocalDateTime.now(), false);
        return customerRepository.save(customer);
    }

    Customer banCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customer.updateToBan();
        return customerRepository.update(customer);
    }

    Customer unbanCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customer.updateToUnban();
        return customerRepository.update(customer);
    }

    void deleteCustomer(Customer customer) {
        customerRepository.delete(customer.getId());
    }
}
