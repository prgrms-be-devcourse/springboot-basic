package com.devcourse.springbootbasic.application.customer.service;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBlackCustomers() {
        return customerRepository.findAllBlackCustomers();
    }

    public Customer registCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public Customer findCustomerByName(String name) {
        return customerRepository.findByName(name)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public Customer deleteCustomerById(UUID customerId) {
        var deletedCustomer = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        return deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

}
