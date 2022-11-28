package com.programmers.customer.service;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void createCustomer(String email, String password, String name) {
        // validateEmail 필요함
        Customer customer = new Customer(email, password, name, LocalDateTime.now());
        customerRepository.insert(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void updateByName(Customer customer) {
        customerRepository.update(customer.getEmail(), customer.getName());
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
