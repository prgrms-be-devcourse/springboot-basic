package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.customer.repository.FileCustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer insert(Customer customer) {
        return customerRepository.insert(customer);
    }

    public Optional<List<Customer>> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> getBlackListCustomers() throws IOException {
        return customerRepository.getAllBlackList().get();
    }
}
