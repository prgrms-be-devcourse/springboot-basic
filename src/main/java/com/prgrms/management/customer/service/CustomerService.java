package com.prgrms.management.customer.service;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findBlackList() {
        return customerRepository.findBlackList();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }

    public void updateCustomer(UUID customerId) {

    }

    public void findById(UUID customerId) {
        customerRepository.findById(customerId);
    }

    public void findByEmail(String email) {
        customerRepository.findByEmail(email);
    }
}
