package com.tangerine.voucher_system.application.customer.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findBlackCustomers() {
        return customerRepository.findAllBlackCustomers();
    }

    public Customer createCustomer(Customer customer) {
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

    public Customer findCustomerByName(Name name) {
        return customerRepository.findByName(name)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public Customer deleteCustomerById(UUID customerId) {
        Optional<Customer> deletedCustomer = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        return deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

}
