package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.repository.user.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public void create(Customer customer) {
        customerRepository.insert(customer);
    }

    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    public void delete(UUID customerId) {
        customerRepository.delete(customerId);
    }
}
