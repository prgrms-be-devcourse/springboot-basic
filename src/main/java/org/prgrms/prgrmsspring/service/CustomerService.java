package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.repository.user.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findBlackAll() {
        return customerRepository.findBlackAll();
    }

    public Customer create(Customer customer) {
        return customerRepository.insert(customer);
    }

    public Customer update(Customer customer) {
        try {
            return customerRepository.update(customer);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_CUSTOMER.getMessage());
        }

    }

    public void delete(UUID customerId) {
        try {
            customerRepository.delete(customerId);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_CUSTOMER.getMessage());
        }

    }
}
