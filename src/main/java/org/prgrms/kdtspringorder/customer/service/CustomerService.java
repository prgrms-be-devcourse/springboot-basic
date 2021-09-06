package org.prgrms.kdtspringorder.customer.service;

import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.customer.repository.abstraction.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBannedCustomers() {
        return customerRepository.getBannedCustomers();
    }
}
