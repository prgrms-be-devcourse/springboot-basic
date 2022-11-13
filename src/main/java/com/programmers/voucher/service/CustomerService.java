package com.programmers.voucher.service;

import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {
    private final String FAIL_GET_BLACK = "파일에서 블랙리스트를 불러오지 못했습니다.";

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllBlack() {
        try {
            return customerRepository.findAllBlack();
        } catch (IOException e) {
            throw new RuntimeException(FAIL_GET_BLACK);
        }
    }
}
