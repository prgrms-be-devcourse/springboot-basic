package com.prgms.management.customer.service;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SimpleCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;

    public SimpleCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void removeCustomerById(UUID id) {
        customerRepository.removeById(id);
    }
}
