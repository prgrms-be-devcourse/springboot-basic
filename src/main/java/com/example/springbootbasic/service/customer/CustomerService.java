package com.example.springbootbasic.service.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllBlackCustomers() {
        return customerRepository.findAllCustomers();
    }
}
