package org.prgrms.kdt.domain.customer.service;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findBlackListCustomers() {
        List<Customer> blackCustomers = customerRepository.findAll();
        logger.info("find Black List Customers {}", blackCustomers);
        return blackCustomers;
    }
}
