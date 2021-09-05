package org.programmers.customer.service;

import org.programmers.customer.model.Customer;
import org.programmers.customer.repository.CustomerJdbcRepository;
import org.programmers.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerJdbcRepository customerJdbcRepository) {
        this.customerRepository = customerJdbcRepository;
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public void create(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findByEmail(String email) {
        Optional<Customer> findCustomer = customerRepository.findByEmail(email);
        return findCustomer.get();
    }

    public void update(String email, String name) {
        customerRepository.update(email, name);
    }

    public void deleteByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
