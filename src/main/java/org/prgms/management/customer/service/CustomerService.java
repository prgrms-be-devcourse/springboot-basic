package org.prgms.management.customer.service;

import org.prgms.management.customer.entity.Customer;
import org.prgms.management.customer.repository.CustomerRepository;
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

    public Customer insert(Customer customer) {
        return customerRepository.insert(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> getByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    public Customer delete(Customer customer) {
        return customerRepository.delete(customer);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
