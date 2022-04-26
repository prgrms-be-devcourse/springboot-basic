package org.prgrms.kdt.service;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerJdbcRepository customerRepository;

    public CustomerService(CustomerJdbcRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.insertCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAllCustomer();
    }
}
