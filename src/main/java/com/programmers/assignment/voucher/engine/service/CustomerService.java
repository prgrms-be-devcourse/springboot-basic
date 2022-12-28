package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.engine.repository.CustomerRepository;
import com.programmers.assignment.voucher.util.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        return customerRepository.save(
                new Customer(UUID.randomUUID(), customerDto.name(), customerDto.email(), LocalDateTime.now())
        );
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerByUuid(UUID uuid) {
        var customer = customerRepository.findByUuid(uuid);
        if (customer.isEmpty()) throw new NoSuchElementException(uuid + " is wrong customer UUID");
        return customer.get();
    }
}
