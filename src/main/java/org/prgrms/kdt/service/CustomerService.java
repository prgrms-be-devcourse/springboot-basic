package org.prgrms.kdt.service;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        try {
            customerRepository.insertCustomer(customer);
        } catch (DuplicateKeyException e) {
            logger.info("email : {} {}", customer.getEmail(), e.getMessage());
            System.out.println(MessageFormat.format("{0} is already exist customer", customer.getEmail()));
        } catch (EmptyResultDataAccessException e) {
            logger.info(e.getMessage());
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAllCustomer();
    }
}
