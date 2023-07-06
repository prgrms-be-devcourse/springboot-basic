package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.dto.CustomerCreateDto;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void save(CustomerCreateDto customerCreateDto) {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName(customerCreateDto.getCustomerName())
                .customerEmail(customerCreateDto.getCustomerEmail())
                .customerType(customerCreateDto.getCustomerType())
                .build();

        customerRepository.save(customer);
    }

    public void update(CustomerUpdateDto customerUpdateDto) {
        customerRepository.update(
                Customer.builder()
                        .customerId(customerUpdateDto.getCustomerId())
                        .customerName(customerUpdateDto.getCustomerName())
                        .customerEmail(customerUpdateDto.getCustomerEmail())
                        .customerType(customerUpdateDto.getCustomerType())
                        .build()
        );
    }

    public Optional<Customer> findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> getBlackList() {
        return customerRepository.getBlackList();
    }

    public void deleteById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
