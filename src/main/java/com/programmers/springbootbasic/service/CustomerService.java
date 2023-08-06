package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.domain.customer.Repository.CustomerRepository;
import com.programmers.springbootbasic.presentation.controller.dto.Customer.CustomerCreationRequest;
import com.programmers.springbootbasic.presentation.controller.dto.Customer.CustomerUpdateRequest;
import com.programmers.springbootbasic.service.dto.Customer.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final String FAIL_TO_CREATE = "유저 생성에 실패했습니다. 입력값을 확인해주세요";
    private static final String FAIL_TO_UPDATE = "유저 업데이트에 실패했습니다. 입력값을 확인해주세요";
    private static final String NO_CUSTOMER_MATCHES_EMAIL = "해당하는 이메일의 고객이 존재하지 않습니다.";
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CustomerCreationRequest request) {
        Customer customer = CustomerMapper.toCustomer(request);
        return customerRepository.save(customer)
                .map(CustomerMapper::toCustomerResponse)
                .orElseThrow(() -> new IllegalArgumentException(FAIL_TO_CREATE));
    }

    public CustomerResponse updateCustomerName(CustomerUpdateRequest request) {
        Customer customer = CustomerMapper.toCustomer(request);

        return customerRepository.update(customer)
                .map(CustomerMapper::toCustomerResponse)
                .orElseThrow(() -> new IllegalArgumentException(FAIL_TO_UPDATE));
    }

    public CustomerResponse findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(CustomerMapper::toCustomerResponse)
                .orElseThrow(() -> new IllegalArgumentException(NO_CUSTOMER_MATCHES_EMAIL));
    }
}
