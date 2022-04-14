package org.prgrms.springbootbasic.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.exception.DuplicateCustomerEmailException;
import org.prgrms.springbootbasic.repository.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UUID createCustomer(String name, String email) {
        logger.info("createCustomer() called");

        validateDuplicateEmail(email);

        var customerId = customerRepository.save(new Customer(UUID.randomUUID(), name, email));
        return customerId;
    }

    private void validateDuplicateEmail(String email) {
        logger.info("validateDuplicateEmail() called");

        var customers = customerRepository.findByEmail(email);
        if (customers.isPresent()) {
            throw new DuplicateCustomerEmailException();
        }
    }

    public List<Customer> findAllCustomers() {
        logger.info("findAllCustomers() called");

        return customerRepository.findAll();
    }
}
