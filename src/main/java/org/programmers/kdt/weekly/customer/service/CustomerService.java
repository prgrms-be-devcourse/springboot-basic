package org.programmers.kdt.weekly.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(String email, String name) {
        Customer customer = new Customer(UUID.randomUUID(), email, name, CustomerType.NORMAL);
        this.customerRepository.insert(customer);

        return customer;
    }

    public List<Customer> getCustomers(CustomerType customerType) {
        return this.customerRepository.findByType(customerType.toString());
    }

    public Optional<Customer> findByEmail(String customerEmail) {
        return this.customerRepository.findByEmail(customerEmail);
    }

    public Customer updateType(Customer customer) {
        return this.customerRepository.update(customer);
    }
}