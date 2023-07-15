package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponseDto;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDto create(String nickname) {
        Customer savedCustomer = customerRepository.save(new Customer(nickname));

        return CustomerResponseDto.toDto(savedCustomer.getCustomerId(), savedCustomer.getNickname());
    }
}
