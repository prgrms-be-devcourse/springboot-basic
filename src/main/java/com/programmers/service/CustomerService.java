package com.programmers.service;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.dto.CustomerCreateRequestDto;
import com.programmers.domain.customer.dto.CustomerResponseDto;
import com.programmers.domain.customer.dto.CustomerUpdateRequestDto;
import com.programmers.domain.customer.dto.CustomersResponseDto;
import com.programmers.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponseDto save(CustomerCreateRequestDto customerDto) {
        Customer requestCustomer = new Customer(customerDto.name());
        Customer customer = customerRepository.save(requestCustomer);

        return new CustomerResponseDto(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerType());
    }

    @Transactional
    public CustomersResponseDto findAll() {
        List<Customer> customers = customerRepository.findAll();

        return new CustomersResponseDto(customers);
    }

    public CustomerResponseDto findById(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return new CustomerResponseDto(customer.get().getCustomerId(), customer.get().getCustomerName(), customer.get().getCustomerType());
    }

    @Transactional
    public CustomerResponseDto update(CustomerUpdateRequestDto customerUpdateRequestDto) {
        Customer requestCustomer = new Customer(customerUpdateRequestDto.id(), customerUpdateRequestDto.name());
        Customer customer = customerRepository.update(requestCustomer);

        return new CustomerResponseDto(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerType());
    }

    @Transactional
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
