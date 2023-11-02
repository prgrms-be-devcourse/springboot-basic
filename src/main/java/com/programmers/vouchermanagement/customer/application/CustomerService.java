package com.programmers.vouchermanagement.customer.application;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponseDto> readAllBlackList() {

        List<Customer> customers = customerRepository.findAllBlack();

        List<CustomerResponseDto> customerResponseDtos = customers.stream()
                .map(customer -> new CustomerResponseDto(customer.getCustomerId(), customer.getName(), customer.getCustomerType()))
                .toList();

        return customerResponseDtos;
    }
}
