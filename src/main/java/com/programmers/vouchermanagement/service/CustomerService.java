package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.request.CreateCustomerRequestDto;
import com.programmers.vouchermanagement.dto.customer.request.GetCustomersRequestDto;
import com.programmers.vouchermanagement.dto.customer.request.UpdateCustomerRequestDto;
import com.programmers.vouchermanagement.dto.customer.response.CustomerResponseDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(CreateCustomerRequestDto request) {
        Optional<Customer> customer = customerRepository.findByEmail(request.email());
        if (customer.isPresent()) {
            throw new IllegalArgumentException("Already exist customer");
        }

        customerRepository.save(new Customer(request.email()));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getCustomers(GetCustomersRequestDto request) {
        List<Customer> customers = customerRepository.findAll(request);
        return customers.stream()
                .map(customer -> CustomerResponseDto.from(customer.getId(), customer.getEmail()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found customer"));
        return CustomerResponseDto.from(customer.getId(), customer.getEmail());
    }

    public void updateCustomer(UUID id, UpdateCustomerRequestDto request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found customer"));
        customerRepository.update(Customer.from(customer.getId(), request.email(), customer.isBlacklisted()));
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
