package com.prgrms.spring.service.customer;

import com.prgrms.spring.controller.dto.request.CustomerCreateRequestDto;
import com.prgrms.spring.domain.customer.Customer;
import com.prgrms.spring.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
        var newCustomer = Customer.newInstance(UUID.randomUUID(), customerCreateRequestDto.getName(), customerCreateRequestDto.getEmail(), LocalDateTime.now());
        customerRepository.insert(newCustomer);
        return newCustomer;
    }

    @Transactional
    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("고객 조회 실패"));
    }

    @Transactional
    public Collection<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
