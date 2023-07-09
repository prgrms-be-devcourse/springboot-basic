package org.prgrms.assignment.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    @Transactional
    public Customer createCustomer(UUID customerId, String name, String email) {
        return customerRepository.insert(of(customerId, name, email));
    }

    @Override
    @Transactional
    public Customer updateCustomer(UUID customerId, String name, String email) {
        return customerRepository.update(of(customerId, name, email));
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByName(String name) {
        return customerRepository.findByName(name).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    @Transactional
    public void deleteAll() {
        customerRepository.deleteAll();
    }

    private Customer of(UUID customerId, String name, String email) {
        return new Customer(customerId, name, email, LocalDateTime.now());
    }
}
