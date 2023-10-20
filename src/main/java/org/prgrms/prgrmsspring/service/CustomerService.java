package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.repository.user.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findBlackAll() {
        return customerRepository.findBlackAll();
    }
}
