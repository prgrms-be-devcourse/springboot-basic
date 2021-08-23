package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException(MessageFormat.format("Cannot find a customer for ID : {0}", customerId))
        );
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        // 이름은 중복 가능
        return customerRepository.findByName(name).orElseThrow(
                () -> new RuntimeException(MessageFormat.format("Cannot find a customer for Name : {0}", name))
        );
    }

    @Override
    public List<Customer> findCustomersByEmail(String email) {
        // email은 중복 가능
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException(MessageFormat.format("Cannot find a customer for email : {0}", email))
        );
    }

    @Override
    public Customer registerToBlacklist(Customer customer) {
        return this.customerRepository.registerToBlackList(customer);
    }

    @Override
    public Customer join(UUID uuid, String name, String email) {
        return customerRepository.save(new Customer(uuid, name, email));
    }

    @Override
    public List<Customer> getBlacklistCustomers() {
        return customerRepository.findBlackListCustomers();
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Boolean isOnBlacklist(Customer customer) {
        return customerRepository.findBlackListCustomers().contains(customer);
    }
}
