package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.request.CreateCustomerRequestDto;
import com.programmers.vouchermanagement.dto.customer.request.GetCustomersRequestDto;
import com.programmers.vouchermanagement.dto.customer.response.CustomerResponseDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(CreateCustomerRequestDto request) {
        Optional<Customer> customer = customerRepository.findByEmail(request.getEmail());
        if (customer.isPresent()) {
            throw new IllegalArgumentException("Already exist customer");
        }

        customerRepository.save(new Customer(request.getEmail()));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getCustomers(GetCustomersRequestDto request) {
        List<Customer> customers = customerRepository.findAll(request);
        return customers.stream()
                .map(customer -> CustomerResponseDto.from(customer.getId(), customer.getEmail()))
                .toList();
    }
}
