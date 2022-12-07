package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.engine.io.ConsoleOutput;
import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.engine.repository.CustomerRepository;
import com.programmers.assignment.voucher.util.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final CustomerRepository customerRepository;

    private static final String CUSTOMER_NAME = "Type your name: ";

    private static final String CUSTOMER_EMAIL = "Type your email: ";

    public CustomerService(ConsoleInput input, ConsoleOutput output, CustomerRepository customerRepository) {
        this.input = input;
        this.output = output;
        this.customerRepository = customerRepository;
    }

    public CustomerDto inputCustomerInfo() {
        String customerName = input.inputCustomerInfo(CUSTOMER_NAME);
        String customerEmail = input.inputCustomerInfo(CUSTOMER_EMAIL);

        return new CustomerDto(customerName, customerEmail);
    }

    public Customer createCustomer(CustomerDto customerDto) {
        return customerRepository.save(
                new Customer(UUID.randomUUID(), customerDto.name(), customerDto.email(), LocalDateTime.now())
        );
    }

    public Customer findCustomerByName(String name) {
        var customer = customerRepository.findByName(name);
        if (customer.isEmpty()) throw new IllegalArgumentException(name + " is wrong customer name");
        return customer.get();
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long customerId) {
        var customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) throw new IllegalArgumentException(customerId + " is wrong customer Id");
        return customer.get();
    }

    public Customer findCustomerByUuid(UUID uuid) {
        var customer = customerRepository.findByUuid(uuid);
        if (customer.isEmpty()) throw new IllegalArgumentException(uuid + " is wrong customer UUID");
        return customer.get();
    }

    public Customer updateCustomer(CustomerDto customerDto, Long customerId) {
        return customerRepository.update(customerDto, customerId);
    }
}
