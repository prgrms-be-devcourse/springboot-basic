package org.prgms.management.service.customer;

import org.prgms.management.model.customer.Customer;
import org.prgms.management.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(String name) {
        var customer =
                Customer.getCustomer(UUID.randomUUID(), name, LocalDateTime.now());
        return customerRepository.insert(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer getByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    public Customer delete(Customer customer) {
        return customerRepository.delete(customer);
    }

    public Customer delete(UUID customerId) {
        var customer = customerRepository.findById(customerId);
        if (customer == null) {
            return null;
        }
        return customerRepository.delete(customer);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
