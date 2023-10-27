package com.programmers.vouchermanagement.customer.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.util.Validator;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> readBlacklist() {
        List<Customer> blacklist = customerRepository.findBlackCustomers();
        return blacklist.stream()
                .map(CustomerResponse::from)
                .toList();
    }

    public CustomerResponse create(String name) {
        Validator.validateCustomerName(name);
        Customer customer = new Customer(UUID.randomUUID(), name);
        customerRepository.save(customer);
        return CustomerResponse.from(customer);
    }

    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerResponse::from)
                .toList();
    }

    public CustomerResponse findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("There is no customer with %s".formatted(customerId)));
        return CustomerResponse.from(customer);
    }

    public CustomerResponse update(UpdateCustomerRequest updateCustomerRequest) {
        validateIdExisting(updateCustomerRequest.customerId());
        Customer customer = new Customer(updateCustomerRequest.customerId(), updateCustomerRequest.name(), updateCustomerRequest.customerType());
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerResponse.from(updatedCustomer);
    }

    public void deleteById(UUID customerId) {
        validateIdExisting(customerId);
        customerRepository.deleteById(customerId);
    }

    private void validateIdExisting(UUID customerId) {
        if (!customerRepository.existById(customerId)) {
            throw new NoSuchElementException("There is no customer with %s".formatted(customerId));
        }
    }
}
