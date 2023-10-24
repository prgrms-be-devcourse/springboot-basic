package com.pgms.part1.domain.customer.service;

import com.pgms.part1.domain.customer.dto.CustomerFileResponseDto;
import com.pgms.part1.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerFileResponseDto> listBlockedCustomers(){
        return customerRepository.listBlockedCustomers();
    }
}
