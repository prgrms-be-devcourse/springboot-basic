package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> findBannedCustomers() {
        return customerRepository.findBannedCustomers();
    }

    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
    }

    public String findCustomerNameById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
        return customer.getName();
    }

    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findByNameLike(name);
    }

    @Transactional(readOnly = false)
    public Customer createCustomer(CustomerDto.CreateRequest customerDto) {
        customerRepository.findByName(customerDto.name()).ifPresent(customer -> {
            throw new IllegalArgumentException(ErrorMessage.CUSTOMER_ALREADY_EXISTS_MESSAGE.getMessage());
        });
        Customer customer = new Customer(customerDto);
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = false)
    public Customer banCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
        customer.updateToBan();
        return customerRepository.update(customer);
    }

    @Transactional(readOnly = false)
    public Customer unbanCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
        customer.updateToUnban();
        return customerRepository.update(customer);
    }

    @Transactional(readOnly = false)
    public void deleteCustomer(UUID id) {
        customerRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage()));
        customerRepository.delete(id);
    }
}
