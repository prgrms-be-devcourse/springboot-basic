package com.prgrms.management.customer.service;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.config.exception.NotFoundException;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findBlackList() {
        return customerRepository.findBlackList();
    }

    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(this.getClass() + ErrorMessageType.NOT_EXIST_EXCEPTION.getMessage()));
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(this.getClass() + ErrorMessageType.NOT_EXIST_EXCEPTION.getMessage()));
    }

    public void updateCustomer(UUID customerId, String customerName) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(this.getClass() + ErrorMessageType.NOT_EXIST_EXCEPTION.getMessage()));
        customer.setName(customerName);
        customerRepository.updateName(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }

}
