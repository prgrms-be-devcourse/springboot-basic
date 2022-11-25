package com.programmers.voucher.service;

import com.programmers.voucher.controller.dto.CustomerRequest;
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

    public Customer create(CustomerRequest customerRequest) {
        return customerRepository.save(customerRequest);
    }

    public Customer findByVoucher(UUID voucherId) {
        return customerRepository.findByVoucher(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(Message.NOT_EXIST_OR_NOT_ASSIGN_VOUCHER.toString()));
    }
}

