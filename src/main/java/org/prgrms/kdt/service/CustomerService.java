package org.prgrms.kdt.service;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.Customers;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerJdbcRepository customerRepository;

    public CustomerService(CustomerJdbcRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.insertCustomer(customer);
    }

    public Customers getAllCustomers() {
        return customerRepository.findAllCustomer();
    }
}
