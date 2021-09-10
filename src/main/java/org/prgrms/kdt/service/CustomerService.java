package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static org.prgrms.kdt.exception.Message.CANNOT_FIND_CUSTOMER;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomer(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format(CANNOT_FIND_CUSTOMER + "{0}", customerId)));
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }
}
