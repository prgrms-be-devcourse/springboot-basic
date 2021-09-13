package org.prgms.order.customer.service;

import org.prgms.order.customer.entity.Customer;
import org.prgms.order.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAllBlackList() {
        return customerRepository.findBlackList();
    }

    @Override
    public void insertBlackListById(UUID customerId) {

    }


    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public Customer createCustomer(String email, String name) {
        var customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerRepository.insert(customer);
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer modifyCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }


}

