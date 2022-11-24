package org.prgrms.voucherapplication.customer.service;

import org.prgrms.voucherapplication.customer.entity.Customer;
import org.prgrms.voucherapplication.customer.repository.BlackListRepository;
import org.prgrms.voucherapplication.customer.repository.CustomerRepository;
import org.prgrms.voucherapplication.customer.dto.ResponseBlacklist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BlackListRepository blackListRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, BlackListRepository blackListRepository) {
        this.customerRepository = customerRepository;
        this.blackListRepository = blackListRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public List<ResponseBlacklist> findBlacklist() {
        return blackListRepository.findAll();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(String email, String name) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerRepository.insert(customer);
    }

    @Override
    public Optional<Customer> getCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }
}
