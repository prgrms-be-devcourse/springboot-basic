package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void save(CustomerCreateRequest customerCreateRequest) {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName(customerCreateRequest.getCustomerName())
                .customerEmail(customerCreateRequest.getCustomerEmail())
                .customerType(customerCreateRequest.getCustomerType())
                .build();

        customerRepository.save(customer);
    }

    public void update(CustomerUpdateRequest customerUpdateRequest) {
        customerRepository.update(
                Customer.builder()
                        .customerId(customerUpdateRequest.getCustomerId())
                        .customerName(customerUpdateRequest.getCustomerName())
                        .customerEmail(customerUpdateRequest.getCustomerEmail())
                        .customerType(customerUpdateRequest.getCustomerType())
                        .build()
        );
    }

    public CustomerResponse findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId);
        return new CustomerResponse(customer);
    }

    public CustomerListResponse findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return new CustomerListResponse(customerList.stream().map(CustomerResponse::new).collect(Collectors.toList()));
    }

    public CustomerListResponse getBlackList() {
        List<Customer> customerList = customerRepository.getBlackList();
        return new CustomerListResponse(customerList.stream().map(CustomerResponse::new).collect(Collectors.toList()));
    }

    public void deleteById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
