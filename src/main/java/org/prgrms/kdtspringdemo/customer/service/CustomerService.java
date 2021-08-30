package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void printAll() {
        Stream<Customer> allCustomer = customerRepository.findAll();
        allCustomer.forEach(System.out::println);
    }

    public void printBlacklist() {
        Stream<Customer> allCustomer = customerRepository.findBlacklist();
        allCustomer.forEach(System.out::println);
    }
}
