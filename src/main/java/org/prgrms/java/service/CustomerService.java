package org.prgrms.java.service;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.prgrms.java.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerRepository.insert(customer);
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(() -> new CustomerException(String.format("Can not find a customer for %s", customerId)));
    }

    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(() -> new CustomerException(String.format("Can not find a customer for %s", name)));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(() -> new CustomerException(String.format("Can not find a customer for %s", email)));
    }

    public Customer getBlackCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .filter(Customer::isBlocked)
                .orElseThrow(() -> new CustomerException(String.format("Can not find a black customer for %s", customerId)));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll().stream()
                .filter(customer -> !customer.isBlocked())
                .collect(Collectors.toList());
    }

    public List<Customer> getAllBlackCustomers() {
        return customerRepository.findAll().stream()
                .filter(Customer::isBlocked)
                .collect(Collectors.toList());
    }

    public Customer updateCustomer(UUID customerId, String name, String email, boolean isBlocked) {
        Customer customer = getCustomerById(customerId);
        customer.setName(name);
        customer.setEmail(email);
        customer.setBlocked(isBlocked);

        return customerRepository.update(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
    }

    public long deleteAllCustomers() {
        return customerRepository.deleteAll();
    }
}
