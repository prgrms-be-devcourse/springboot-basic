package com.programmers.springbootbasic.domain.customer.service;

import com.programmers.springbootbasic.common.utils.UUIDValueStrategy;
import com.programmers.springbootbasic.domain.customer.dto.CustomerRequestDto;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UUIDValueStrategy uuidValueStrategy;

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Customer> findAllBlacklistCustomer() {
        return customerRepository.findBlacklist();
    }

    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        if (customerRepository.findByEmail(customerRequestDto.getEmail()).isPresent()) {
            log.warn(ErrorMsg.CUSTOMER_ALREADY_EXIST.getMessage() + String.format(" Email: %s", customerRequestDto.getEmail()));
            throw new IllegalArgumentException(ErrorMsg.CUSTOMER_ALREADY_EXIST.getMessage());
        }
        Customer customer = Customer.builder()
                .customerId(uuidValueStrategy.generateUUID())
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .build();
        return customerRepository.save(customer);
    }

    public void addCustomerInBlacklist(CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail()).orElseThrow(
                () -> {
                    log.warn(ErrorMsg.CUSTOMER_NOT_FOUND.getMessage());
                    return new IllegalArgumentException(ErrorMsg.CUSTOMER_NOT_FOUND.getMessage());
                });
        if (customer.isBlacklist()) {
            throw new RuntimeException(ErrorMsg.ALREADY_IN_BLACKLIST.getMessage());
        }
        customer.addBlacklist();
        customerRepository.update(customer);
    }

    public void removeCustomerFromBlacklist(CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail()).orElseThrow(
                () -> {
                    log.warn(ErrorMsg.CUSTOMER_NOT_FOUND.getMessage());
                    return new IllegalArgumentException(ErrorMsg.CUSTOMER_NOT_FOUND.getMessage());
                });
        if (!customer.isBlacklist()) {
            throw new RuntimeException(ErrorMsg.NOT_IN_BLACKLIST.getMessage());
        }
        customer.removeBlacklist();
        customerRepository.update(customer);
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }
}
