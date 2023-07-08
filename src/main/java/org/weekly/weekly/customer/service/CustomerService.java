package org.weekly.weekly.customer.service;

import org.springframework.stereotype.Service;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerCreationRequest creationRequest) {
        Customer customer = creationRequest.toCustomer();
        this.customerRepository.insert(customer);
        return customer;
    }

    public void deleteCustomer(CustomerUpdateRequest updateRequest) {
        String email = updateRequest.email();
        this.customerRepository.deleteByEmail(email);
    }

    public void deleteAllCustomer() {
        this.customerRepository.deleteAll();
    }

    public void searchDetailCustomer(CustomerUpdateRequest updateRequest) {
        String email = updateRequest.email();
        this.customerRepository.findByEmail(email);
    }

    public void searchAllCustomer() {
        this.customerRepository.findAll();
    }
}
