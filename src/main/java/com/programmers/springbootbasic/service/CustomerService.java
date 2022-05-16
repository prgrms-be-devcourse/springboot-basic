package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.repository.CustomerJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.programmers.springbootbasic.validation.CustomerValidator.*;

@Service
public class CustomerService {

    private final CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    public CustomerService(CustomerJdbcRepository customerJdbcRepository) {
        this.customerJdbcRepository = customerJdbcRepository;
    }

    public Customer createCustomer(String customerId, String name) {
        validateCustomerName(name);

        if(getCustomerById(customerId).isPresent())
            throw new IllegalArgumentException(DUPLICATE_CUSTOMER_ID_EXCEPTION_MESSAGE);

        return customerJdbcRepository.insert(new Customer(customerId, name));
    }

    public Optional<Customer> getCustomerById(String customerId) {
        validateCustomerId(customerId);

        return customerJdbcRepository.findById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerJdbcRepository.findAll();
    }

    public void deleteCustomerById(String customerId) {
        validateCustomerId(customerId);

        customerJdbcRepository.deleteById(customerId);
    }

    public void deleteAllCustomers() {
        customerJdbcRepository.deleteAll();
    }

    public static void validateCustomerId(String customerId) {
        if (!isValidCustomerId(customerId))
            throw new IllegalArgumentException(INVALID_CUSTOMER_ID_EXCEPTION_MESSAGE);
    }

    public static void validateCustomerName(String name) {
        if (!isValidCustomerName(name))
            throw new IllegalArgumentException(INVALID_CUSTOMER_NAME_EXCEPTION_MESSAGE);
    }

}