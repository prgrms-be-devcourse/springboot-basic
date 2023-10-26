package org.programmers.springorder.customer.service;

import org.programmers.springorder.customer.dto.CustomerRequestDto;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = Customer.of(UUID.randomUUID(), customerRequestDto);
        System.out.println("CustomerService.createCustomer");
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<CustomerResponseDto> getBlackList() {
        return customerRepository.findAllBlackList()
                .stream()
                .map(CustomerResponseDto::of)
                .toList();
    }
}
