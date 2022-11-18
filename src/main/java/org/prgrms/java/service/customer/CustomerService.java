package org.prgrms.java.service.customer;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.prgrms.java.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(() -> new CustomerException(String.format("Can not find a customer for %s", customerId)));
    }

    public Customer getBlackCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .filter(Customer::isBlocked)
                .orElseThrow(() -> new CustomerException(String.format("Can not find a black customer for %s", customerId)));
    }

    public Collection<Customer> getAllCustomers() {
        return customerRepository.findAll().stream()
                .filter(customer -> !customer.isBlocked())
                .collect(Collectors.toList());
    }

    public Collection<Customer> getAllBlackCustomers() {
        return customerRepository.findAll().stream()
                .filter(Customer::isBlocked)
                .collect(Collectors.toList());
    }
}
