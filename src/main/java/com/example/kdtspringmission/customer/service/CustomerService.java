package com.example.kdtspringmission.customer.service;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(
        CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }
}
