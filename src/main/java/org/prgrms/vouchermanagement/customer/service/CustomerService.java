package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.exception.customer.CustomerAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(String name, String email) {
        if (customerRepository.isPresent(email)) {
            throw new CustomerAlreadyExistException();
        }

        return customerRepository.save(Customer.createNormalCustomer(UUID.randomUUID(), name, email, LocalDateTime.now()));
    }

    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    public Optional<Customer> findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
