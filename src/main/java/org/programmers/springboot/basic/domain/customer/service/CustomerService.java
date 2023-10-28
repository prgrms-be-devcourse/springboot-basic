package org.programmers.springboot.basic.domain.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.programmers.springboot.basic.domain.customer.exception.BlackCustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateBlackCustomerException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateEmailException;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapper;
import org.programmers.springboot.basic.domain.customer.repository.CustomerRepository;
import org.programmers.springboot.basic.domain.customer.service.validate.EmailValidator;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UUIDGenerator uuidGenerator;
    private final EmailValidator emailValidator;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UUIDGenerator uuidGenerator, EmailValidator emailValidator) {
        this.customerRepository = customerRepository;
        this.uuidGenerator = uuidGenerator;
        this.emailValidator = emailValidator;
    }

    @Transactional
    public void create(CustomerRequestDto customerRequestDto) {

        String email = customerRequestDto.getEmail();

        emailValidator.validate(email);

        Customer customer = CustomerEntityMapper.INSTANCE.mapToEntityWithGeneratorForAllArgs(customerRequestDto, uuidGenerator);

        try {
            findByEmail(email);
            log.warn("Duplicate email {} is already exists", email);
            throw new DuplicateEmailException();
        } catch (CustomerNotFoundException e) {
            add(customer);
        }
    }

    public List<CustomerResponseDto> findAll() {

        List<Customer> customers = getAll();
        return customers.stream()
                .map(CustomerEntityMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<CustomerResponseDto> findByCustomerIsBlack() {

        List<Customer> customers = findBlackCustomer();
        return customers.stream()
                .map(CustomerEntityMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    @Transactional
    public void addBlackCustomer(CustomerRequestDto customerRequestDto) {

        UUID customerId = customerRequestDto.getCustomerId();
        Customer customer = findById(customerId);
        if (customer.getCustomerType().equals(CustomerType.BLACK)) {
            log.warn("customer of customerId {} is already exists from blacklist", customerId);
            throw new DuplicateBlackCustomerException();
        }
        customer.setBlack();
        update(customer);
    }

    @Transactional
    public void deleteCustomer(CustomerRequestDto customerRequestDto) {

        UUID customerId = customerRequestDto.getCustomerId();
        Customer customer = findById(customerId);
        delete(customer);
    }

    @Transactional
    public void deleteBlackCustomer(CustomerRequestDto customerRequestDto) {

        UUID customerId = customerRequestDto.getCustomerId();
        Customer customer = findById(customerId);
        if (customer.getCustomerType().equals(CustomerType.NORMAL)) {
            log.warn("customer of customerId {} is not exists from blacklist", customerId);
            throw new BlackCustomerNotFoundException();
        }
        customer.setNormal();
        update(customer);
    }

    public void add(Customer customer) {
        this.customerRepository.add(customer);
    }

    public Customer findById(UUID customerId) {

        return this.customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("No customer found for customerId: {}", customerId);
                    return new CustomerNotFoundException();
                });
    }

    public Customer findByEmail(String email) {

        return this.customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("No customer found for email: {}", email);
                    return new CustomerNotFoundException();
                });
    }

    public List<Customer> getAll() {
        return this.customerRepository.getAll();
    }

    public List<Customer> findBlackCustomer() {
        return this.customerRepository.getBlack();
    }

    public void update(Customer customer) {
        this.customerRepository.update(customer);
    }

    public void delete(Customer customer) {
        this.customerRepository.delete(customer);
    }

    @Transactional
    public void deleteAll() {
        this.customerRepository.deleteAll();
    }
}
