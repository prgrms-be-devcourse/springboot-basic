package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.repository.customer.jdbc.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JdbcCustomerService {

    private JdbcCustomerRepository customerRepository;

    public JdbcCustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.insert(customer);
    }

    public Optional<List<Customer>> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
