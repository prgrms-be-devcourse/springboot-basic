package com.prgrms.springbasic.domain.customer.service;

import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (isEmailAlreadyExists(request.email())) {
            throw new IllegalArgumentException("Email already exists: " + request.email());
        }
        Customer customer = new Customer(UUID.randomUUID(), request.name(), request.email());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public List<CustomerResponse> findAllBlackList() {
        return customerRepository.findAllBlackList().stream()
                .map(CustomerResponse::from)
                .toList();
    }

    private boolean isEmailAlreadyExists(String email) {
        Optional<Customer> existingCustomer = customerRepository.findCustomerByEmail(email);
        return existingCustomer.isPresent();
    }
}
