package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.customer.CustomerDto;
import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataNotExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private static final String CUSTOMER = "customer";
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(String name) {
        return new CustomerDto(customerRepository.insert(new Customer(UUID.randomUUID(), name)));
    }

    public CustomerDto findCustomerByCustomerId(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerDto::new)
                .orElseThrow(() -> new DataNotExistException(customerId.toString(), CUSTOMER));
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerDto::new)
                .toList();
    }

    public CustomerDto updateCustomer(UUID customerId, String customerName) {
        return new CustomerDto(customerRepository.update(new Customer(customerId, customerName)));
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
    }
}
