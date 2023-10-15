package com.prgrms.springbasic.domain.customer.service;

import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> findAllBlackList() {
        return customerRepository.findAllBlackList().stream()
                .map(CustomerResponse::from)
                .toList();
    }
}
