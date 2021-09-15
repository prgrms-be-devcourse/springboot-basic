package com.programmers.kdtspringorder.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final com.programmers.kdtspringorder.customer.repository.CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<com.programmers.kdtspringorder.customer.model.Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public com.programmers.kdtspringorder.customer.model.Customer insert(com.programmers.kdtspringorder.customer.model.Customer customer) {
        return customerRepository.insert(customer);
    }

    @Override
    public Optional<com.programmers.kdtspringorder.customer.model.Customer> findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<com.programmers.kdtspringorder.customer.model.Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public com.programmers.kdtspringorder.customer.model.Customer createCustomer(String email, String name) {
        com.programmers.kdtspringorder.customer.model.Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return customerRepository.insert(customer);
    }

}
