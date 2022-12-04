package org.prgms.springbootbasic.service;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerCreateDTO;
import org.prgms.springbootbasic.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    public Customer createCustomer(CustomerCreateDTO customerCreateDTO) {
        return customerRepository.save(new Customer(UUID.randomUUID(), customerCreateDTO.name(), customerCreateDTO.email()));
    }

    public Customer updateLastLoginAt(Customer customer) {
        customer.setLastLoginAt(LocalDateTime.now());
        return customerRepository.updateLastLoginAt(customer);
    }

    public Optional<Customer> findOneCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> findCustomers(UUID voucherId) {
        return customerRepository.findByVoucherId(voucherId);
    }

    public UUID deleteCustomer(UUID customerId) {
        return customerRepository.deleteById(customerId);
    }
}
