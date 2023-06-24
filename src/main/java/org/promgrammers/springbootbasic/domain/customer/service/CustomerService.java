package org.promgrammers.springbootbasic.domain.customer.service;

import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.CustomerRepository;
import org.promgrammers.springbootbasic.exception.EmptyListException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomersResponse findAll() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            throw new EmptyListException("저장된 블랙 리스트 고객이 없습니다."); // 예외를 던지는 처리
        }

        return new CustomersResponse(customers.stream()
                .map(customer -> new CustomerResponse(customer.customerId(), customer.customerType()))
                .collect(Collectors.toList()));
    }
}
