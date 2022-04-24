package com.prgms.management.customer.service;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import com.prgms.management.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleBlackCustomerService implements BlackCustomerService {
    private final CustomerRepository customerRepository;

    public SimpleBlackCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findByType(CustomerType.BLACK);
    }
}
