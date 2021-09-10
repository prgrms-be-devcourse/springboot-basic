package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllBlacklist() {
        return customerRepository.findBlacklist();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
