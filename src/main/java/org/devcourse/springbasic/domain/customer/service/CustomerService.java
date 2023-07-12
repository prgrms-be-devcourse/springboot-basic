package org.devcourse.springbasic.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.dao.CustomerRepository;
import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public UUID save(CustomerDto.SaveRequestDto saveRequestDto) {
        Customer customer = Customer.builder()
                .customerId(saveRequestDto.getCustomerId())
                .email(saveRequestDto.getEmail())
                .name(saveRequestDto.getName())
                .createdAt(saveRequestDto.getCreatedAt())
                .build();
        return customerRepository.save(customer);
    }

    @Transactional
    public UUID update(CustomerDto.UpdateRequestDto updateRequestDto) {
        Customer customer = Customer.builder()
                .customerId(updateRequestDto.getCustomerId())
                .email(updateRequestDto.getEmail())
                .name(updateRequestDto.getName())
                .build();
        return customerRepository.update(customer);
    }

    @Transactional
    public void lastLoginUpdate(CustomerDto.LoginRequestDto loginRequestDto) {
        Customer customer = Customer.builder()
                .customerId(loginRequestDto.getCustomerId())
                .lastLoginAt(loginRequestDto.getLastLoginAt())
                .build();
        customerRepository.lastLoginUpdate(customer);
    }

    public List<CustomerDto.ResponseDto> findAll() {

        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> new CustomerDto.ResponseDto(customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public CustomerDto.ResponseDto findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId);
        return new CustomerDto.ResponseDto(customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }

    public CustomerDto.ResponseDto findByName(String name) {
        Customer customer = customerRepository.findByName(name);
        return new CustomerDto.ResponseDto(customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }

    public CustomerDto.ResponseDto findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return new CustomerDto.ResponseDto(customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }
}
