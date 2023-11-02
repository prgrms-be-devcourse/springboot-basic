package org.programmers.springorder.service;

import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.dto.customer.CustomerRequestDto;
import org.programmers.springorder.dto.customer.CustomerResponseDto;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.repository.customer.CustomerRepository;
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
        Customer customer = Customer.of(customerRequestDto);
        customerRepository.insert(customer);
        return customer;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getBlackList() {
        return customerRepository.findAllBlackList()
                .stream()
                .map(CustomerResponseDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.CUSTOMER_ID_NOT_EXIST_MESSAGE));
    }
}
