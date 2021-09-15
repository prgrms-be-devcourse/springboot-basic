package org.prgrms.kdt.customer;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(UUID customerId, String name, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt){
        customerRepository.insert(new Customer(customerId, name, email, createdAt, lastLoginAt));
    }

}
