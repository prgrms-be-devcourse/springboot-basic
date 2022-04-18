package org.prgms.management.customer.service;

import org.prgms.management.customer.entity.Customer;
import org.prgms.management.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
@Service
public class customerService {
    private final CustomerRepository customerRepository;

    public customerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> insert(Customer customer) {
        return customerRepository.insert(customer);
    }

    public Map<UUID, Customer> getAll() {
        return customerRepository.getAll();
    }

    public Optional<Customer> getById(UUID customerId) {
        return customerRepository.getById(customerId);
    }

    public Optional<Customer> getByName(String name) {
        return customerRepository.getByName(name);
    }

    public Optional<Customer> update(Customer customer) {
        return customerRepository.update(customer);
    }

    public Optional<Customer> delete(UUID customerId) {
        return customerRepository.delete(customerId);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
