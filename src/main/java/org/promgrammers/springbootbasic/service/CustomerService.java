package org.promgrammers.springbootbasic.service;

import org.promgrammers.springbootbasic.domain.Customer;
import org.promgrammers.springbootbasic.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomersResponse findAll() {
        List<Customer> customers = customerRepository.findAll();
        return new CustomersResponse(customers.stream()
                .map(customer -> new CustomerResponse(customer.customerId(), customer.customerType()))
                .collect(Collectors.toList()));
    }
}
