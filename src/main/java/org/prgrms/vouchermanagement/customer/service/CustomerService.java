package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.exception.customer.CustomerAlreadyExistException;
import org.prgrms.vouchermanagement.exception.customer.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(String name, String email) {
        if (customerRepository.isPresent(email)) {
            logger.error("[ERROR] Customer already exist error");
            throw new CustomerAlreadyExistException();
        }

        return customerRepository.save(Customer.createNormalCustomer(UUID.randomUUID(), name, email, LocalDateTime.now()));
    }

    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(CustomerNotFoundException::new);
    }
}
