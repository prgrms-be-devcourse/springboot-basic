package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Map<UUID, Customer> getCustomerBlackList(){
        return customerRepository.getCustomerTypeBlackList();
    }
}
