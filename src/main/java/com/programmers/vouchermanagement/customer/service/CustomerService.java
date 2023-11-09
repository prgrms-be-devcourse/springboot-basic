package com.programmers.vouchermanagement.customer.service;

import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer(createCustomerRequest.name(), createCustomerRequest.isBlack());
        customerRepository.insert(customer);
        return CustomerResponse.from(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> readAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> readAllBlackCustomer() {
        List<Customer> blacklist = customerRepository.findAllBlackCustomer();
        if (blacklist.isEmpty()) {
            return Collections.emptyList();
        }
        return blacklist.stream().map(CustomerResponse::from).toList();
    }
}
