package com.prgms.kdtspringorder.application;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prgms.kdtspringorder.domain.model.customer.Customer;
import com.prgms.kdtspringorder.domain.model.customer.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Map<UUID, Customer> findAllBlackList() {
        return customerRepository.findAll();
    }
}
