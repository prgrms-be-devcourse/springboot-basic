package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponseDto;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public CustomerResponseDto findById(UUID customerID) {
        Customer customer = customerRepository.findById(customerID);
        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public CustomerResponseDto findByNickname(String nickname) {
        Customer customer = customerRepository.findByNickname(nickname);
        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }
}
