package com.prgrms.springbasic.domain.customer.service;

import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = new Customer(UUID.randomUUID(), customerRequest.name(), customerRequest.email());
        return customerRepository.save(customer);
    }

    public List<CustomerResponse> findAllBlackList() {
        return customerRepository.findAllBlackList().stream()
                .map(CustomerResponse::from)
                .toList();
    }
}
