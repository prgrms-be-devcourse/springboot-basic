package com.programmers.springbootbasic.domain.customer.service;

import com.programmers.springbootbasic.domain.customer.dto.CustomerRequestDto;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public List<Customer> findAllBlacklistCustomer() {
        return customerRepository.findBlacklist();
    }

    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        if (customerRepository.findByEmail(customerRequestDto.getEmail()).isPresent()) {
            log.warn(ErrorMsg.customerAlreadyExist.getMessage() + String.format(" Email: %s", customerRequestDto.getEmail()));
            throw new IllegalArgumentException(ErrorMsg.customerAlreadyExist.getMessage());
        }
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .build();
        return customerRepository.save(customer);
    }

    public void addCustomerInBlacklist(CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail()).orElseThrow(
                () -> {
                    log.warn(ErrorMsg.customerNotFound.getMessage());
                    return new IllegalArgumentException(ErrorMsg.customerNotFound.getMessage());
                });
        if (customer.isBlacklist()) {
            throw new RuntimeException(ErrorMsg.alreadyInBlacklist.getMessage());
        }
        customer.addBlacklist();
        customerRepository.update(customer);
    }

    public void removeCustomerFromBlacklist(CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail()).orElseThrow(
                () -> {
                    log.warn(ErrorMsg.customerNotFound.getMessage());
                    return new IllegalArgumentException(ErrorMsg.customerNotFound.getMessage());
                });
        if (!customer.isBlacklist()) {
            throw new RuntimeException(ErrorMsg.notInBlacklist.getMessage());
        }
        customer.removeBlacklist();
        customerRepository.update(customer);
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }
}
