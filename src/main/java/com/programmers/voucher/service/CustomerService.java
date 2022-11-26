package com.programmers.voucher.service;

import com.programmers.voucher.controller.dto.CustomerCreateRequest;
import com.programmers.voucher.io.Message;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(CustomerCreateRequest customerCreateRequest) {
        return customerRepository.save(customerCreateRequest);
    }

    public Customer findByVoucher(UUID voucherId) {
        return customerRepository.findByVoucher(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(Message.NOT_EXIST_OR_NOT_ASSIGN_VOUCHER.toString()));
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(Message.NOT_EXIST_CUSTOMER.toString()));
    }
}

