package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllBadCustomer() {
        return customerRepository.findAllBadCustomer();
    }

}
