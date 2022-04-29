package org.programmers.kdtspring.service;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.user.Email;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(String email, String name) {
        log.info("[CustomerService] createCustomer() called");

        var customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerRepository.insert(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}