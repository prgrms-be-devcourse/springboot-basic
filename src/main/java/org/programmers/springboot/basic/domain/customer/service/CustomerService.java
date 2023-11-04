package org.programmers.springboot.basic.domain.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.customer.exception.*;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerMapper;
import org.programmers.springboot.basic.domain.customer.repository.CustomerRepository;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UUIDGenerator uuidGenerator;

    @Transactional
    public void create(CustomerRequestDto customerRequestDto) {

        Email email = customerRequestDto.getEmail();

        email.validate();
        Customer customer = CustomerMapper.INSTANCE.mapToEntityAllArgsWithGenerator(customerRequestDto, uuidGenerator);

        if (isDuplicate(customer)) {
            log.warn("Duplicate email {} is already exists", email);
            throw new DuplicateEmailException();
        } else {
            customerRepository.save(customer);
        }
    }

    private boolean isDuplicate(Customer customer) {
        return customerRepository.findByEmail(customer.getEmail()).isPresent();
    }

    public List<CustomerResponseDto> findAllCustomer() {

        List<Customer> customers = findAll();
        return customers.stream()
                .map(CustomerMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<CustomerResponseDto> findByCustomerIsBlack() {

        List<Customer> customers = findBlackCustomer();
        return customers.stream()
                .map(CustomerMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    @Transactional
    public void addBlackCustomer(CustomerRequestDto customerRequestDto) {

        UUID customerId = customerRequestDto.getCustomerId();

        Customer customer = findById(customerId);
        customer.setBlack();
        customerRepository.update(customer);
    }

    @Transactional
    public void deleteCustomer(CustomerRequestDto customerRequestDto) {

        UUID customerId = customerRequestDto.getCustomerId();

        Customer customer = findById(customerId);
        customerRepository.delete(customer);
    }

    @Transactional
    public void deleteBlackCustomer(CustomerRequestDto customerRequestDto) {

        UUID customerId = customerRequestDto.getCustomerId();

        Customer customer = findById(customerId);
        customer.setNormal();
        customerRepository.update(customer);
    }

    public Customer findById(UUID customerId) {

        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("No customer found for customerId: {}", customerId);
                    return new CustomerNotFoundException();
                });
    }

    public CustomerResponseDto findByEmail(Email email) {

        email.validate();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("No customer found for email: {}", email);
                    return new CustomerNotFoundException();
                });
        return CustomerMapper.INSTANCE.mapToResponseDto(customer);
    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findBlackCustomer() {
        return customerRepository.findBlack();
    }

    @Transactional
    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
