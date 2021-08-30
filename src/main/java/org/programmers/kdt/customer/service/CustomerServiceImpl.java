package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer signUp(UUID customerId, String name, String email) {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(customerId, name, email, now, now);
        try {
            customerRepository.insert(customer);
            logger.info("New Customer has been successfully added to customer list");
        } catch (IOException e) {
            logger.error("Customer SignUp Failed\n{}", e.getMessage());
            throw new RuntimeException(MessageFormat.format("Failed to SignUp : {}", customer));
        }
        return customer;
    }

    @Override
    public Optional<Customer> findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.fineByEmail(email);
    }

    @Override
    public Customer addToBlacklist(Customer customer) {
        try {
            Customer newBlacklistedCustomer = customerRepository.registerToBlacklist(customer);
            logger.info("Customer({}) has been added into blacklist", customer.getCustomerId());
            return newBlacklistedCustomer;
        } catch (IOException e) {
            logger.error("Failed to add Customer({}) into blacklist.\n{}", customer.getCustomerId(), e.getMessage());
            throw new RuntimeException(MessageFormat.format("Failed to add a Customer({0}) to blacklist", customer.getCustomerId()));
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllBlacklistCustomer() {
        return customerRepository.findAllBlacklistCustomer();
    }

    @Override
    public Optional<Customer> removeCustomer(UUID customerId) {
        return customerRepository.deleteCustomer(customerId);
    }

    @Override
    public Boolean isBlacklisted(Customer customer) {
        return customerRepository.findAllBlacklistCustomer().contains(customer);
    }

    @Override
    public String getPrintFormat(Customer customer) {
        return MessageFormat
                .format("<< Customer Information >>\nCustomer ID : {0}\nCustomer Name : {1}\nCustomer Email : {2}\nLast Login At : {3}\nSign Up At : {4}",
                        customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }
}
