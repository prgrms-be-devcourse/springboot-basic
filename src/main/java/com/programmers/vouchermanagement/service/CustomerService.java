package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.common.ErrorMessage;
import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> findBannedCustomers() {
        return customerRepository.findBannedCustomers();
    }

    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
    }

    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findByNameLike(name);
    }

    public Customer createCustomer(String name) {
        customerRepository.findByName(name).ifPresent(customer -> {
            throw new IllegalArgumentException(ErrorMessage.CUSTOMER_ALREADY_EXISTS_MESSAGE.getMessage());
        });
        Customer customer = new Customer(UUID.randomUUID(), name);
        return customerRepository.save(customer);
    }

    public Customer banCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
        customer.updateToBan();
        return customerRepository.update(customer);
    }

    public Customer unbanCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
        customer.updateToUnban();
        return customerRepository.update(customer);
    }

    public void deleteCustomer(UUID id) {
        int affectedRow = customerRepository.delete(id);
        if (affectedRow == 0) {
            throw new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage());
        }
    }
}
