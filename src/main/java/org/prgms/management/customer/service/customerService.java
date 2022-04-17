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

    public Map<UUID, Customer> getAllCustomer() {
        return customerRepository.getAll();
    }

    public Optional<Customer> insertCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }
}
