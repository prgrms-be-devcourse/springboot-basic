package org.prgms.kdt.application.customer.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer join(Customer customer) {
        return customerRepository.insert(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    public void deleteCustomerById(UUID customerId) {
        customerRepository.delete(customerId);
    }
}
