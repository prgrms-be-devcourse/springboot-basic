package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.CustomerDto;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.jdbcRepository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleCustomerService implements CustomerService{

    private final CustomerJdbcRepository customerRepository;

    public SimpleCustomerService(CustomerJdbcRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<CustomerEntity> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<CustomerEntity> createCustomer(String email, String name) {
        CustomerEntity customer = new CustomerEntity(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerRepository.insert(customer);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<CustomerEntity> getCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public Optional<CustomerEntity> updateCustomer(CustomerDto customer) {
        var customerEntity = customerRepository.findById(customer.getCustomerId()).get();
        var updateCustomer = CustomerEntity.builder()
                .customerId(customerEntity.getCustomerId())
                .name(customer.getName())
                .email(customer.getEmail())
                .lastLoginAt(LocalDateTime.now())
                .createdAt(customerEntity.getCreatedAt())
                .build();
        return Optional.ofNullable(customerRepository.update(updateCustomer));
    }
}
