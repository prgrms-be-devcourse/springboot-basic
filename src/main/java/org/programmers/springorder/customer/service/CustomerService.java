package org.programmers.springorder.customer.service;

import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponseDto> getBlackList() {
        return customerRepository.findAllBlackList()
                .stream()
                .map(CustomerResponseDto::of)
                .toList();
    }
}
