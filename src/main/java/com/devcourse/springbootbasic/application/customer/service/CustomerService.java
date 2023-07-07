package com.devcourse.springbootbasic.application.customer.service;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Customer findCustomerById(Customer customer) {
        return customerRepository.findById(customer.getCustomerId())
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText()));
    }

    public Customer findCustomerByName(Customer customer) {
        return customerRepository.findByName(customer.getName())
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText()));
    }

    public Customer findCustomerByEmail(Customer customer) {
        return customerRepository.findByEmail(customer.getEmail())
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText()));
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public Customer deleteCustomerById(Customer customer) {
        var deletedCustomer = customerRepository.findById(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText()));
    }

    public Customer deleteCustomerByName(Customer customer) {
        var deletedCustomer = customerRepository.findByName(customer.getName());
        customerRepository.deleteByName(customer.getName());
        return deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText()));
    }

    public Customer deleteCustomerByEmail(Customer customer) {
        var deletedCustomer = customerRepository.findByEmail(customer.getEmail());
        customerRepository.deleteByEmail(customer.getEmail());
        return deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText()));
    }

}
